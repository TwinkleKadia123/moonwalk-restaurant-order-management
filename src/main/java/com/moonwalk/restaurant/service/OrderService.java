/**
 * 
 */
package com.moonwalk.restaurant.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moonwalk.restaurant.dto.CountdownResponse;
import com.moonwalk.restaurant.dto.CreateOrderItemRequest;
import com.moonwalk.restaurant.dto.CreateOrderRequest;
import com.moonwalk.restaurant.dto.EstimationExecutionResponse;
import com.moonwalk.restaurant.dto.EstimationResult;
import com.moonwalk.restaurant.dto.OrderResponse;
import com.moonwalk.restaurant.entity.EstimationExecution;
import com.moonwalk.restaurant.entity.MenuItem;
import com.moonwalk.restaurant.entity.Order;
import com.moonwalk.restaurant.entity.OrderItem;
import com.moonwalk.restaurant.entity.OrderStatus;
import com.moonwalk.restaurant.entity.Restaurant;
import com.moonwalk.restaurant.repository.EstimationExecutionRepository;
import com.moonwalk.restaurant.repository.MenuItemRepository;
import com.moonwalk.restaurant.repository.OrderRepository;
import com.moonwalk.restaurant.repository.RestaurantRepository;
import com.moonwalk.restaurant.strategy.KitchenState;
import com.moonwalk.restaurant.strategy.TimeEstimationStrategy;

@Service
@Transactional
public class OrderService {

    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    private final EstimationExecutionRepository estimationExecutionRepository;
    private final KitchenService kitchenService;
    private final TimeEstimationStrategy estimationStrategy;
    
    private static final String ESTIMATION_ALGORITHM =
            "RESOURCE_AWARE";

    public OrderService(
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository,
            OrderRepository orderRepository,
            EstimationExecutionRepository estimationExecutionRepository,
            KitchenService kitchenService,
            TimeEstimationStrategy estimationStrategy) {

        this.restaurantRepository = restaurantRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.estimationExecutionRepository =
                estimationExecutionRepository;
        this.kitchenService = kitchenService;
        this.estimationStrategy = estimationStrategy;
    }

    public OrderResponse createOrder(
            CreateOrderRequest request) {

        LocalDateTime executionStart =
                LocalDateTime.now();

        // 1. Validate restaurant
        Restaurant restaurant =
                restaurantRepository.findById(
                        request.getRestaurantId()
                ).orElseThrow(() ->
                        new IllegalArgumentException(
                                "Restaurant not found: "
                                        + request.getRestaurantId()
                        )
                );

        // 2. Create order
        Order order = new Order();

        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.PLACED);
        order.setCreatedAt(executionStart);

        // 3. Validate and add order items
        for (CreateOrderItemRequest itemRequest
                : request.getItems()) {

            MenuItem menuItem =
                    menuItemRepository.findById(
                            itemRequest.getMenuItemId()
                    ).orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Menu item not found: "
                                            + itemRequest.getMenuItemId()
                            )
                    );

            // Make sure menu item belongs to this restaurant
            if (!menuItem.getRestaurant()
                    .getId()
                    .equals(restaurant.getId())) {

                throw new IllegalArgumentException(
                        "Menu item does not belong to restaurant"
                );
            }

            OrderItem orderItem = new OrderItem();

            orderItem.setMenuItem(menuItem);
            orderItem.setQuantity(
                    itemRequest.getQuantity()
            );

            order.addOrderItem(orderItem);
        }

        // 4. Get current kitchen state
        KitchenState kitchenState =
                kitchenService.getKitchenState(
                        restaurant.getId()
                );

        // 5. Calculate preparation time
        EstimationResult estimation =
                estimationStrategy.estimate(
                        order,
                        kitchenState
                );

        // 6. Store estimated ready time
        order.setEstimatedReadyAt(
                estimation.getEstimatedReadyAt()
        );

        // 7. Save order
        Order savedOrder =
                orderRepository.save(order);

        // 8. Calculate actual execution elapsed time
        long elapsedSeconds =
                Duration.between(
                        executionStart,
                        LocalDateTime.now()
                ).toMillis() / 1000;

        // 9. Record estimation execution
        EstimationExecution execution =
                new EstimationExecution();

        execution.setOrder(savedOrder);
        execution.setExecutionTimestamp(
                LocalDateTime.now()
        );
        execution.setElapsedTimeSeconds(
                elapsedSeconds
        );
        execution.setEstimatedTimeMinutes(
                estimation.getEstimatedTimeMinutes()
        );
        execution.setOrderStatus(
                savedOrder.getStatus()
        );
        execution.setAlgorithmUsed(
                estimation.getAlgorithmUsed()
        );
        execution.setEstimatedReadyAt(
                estimation.getEstimatedReadyAt()
        );

        estimationExecutionRepository.save(execution);

        // 10. Return response
        return new OrderResponse(
                savedOrder.getId(),
                restaurant.getId(),
                savedOrder.getStatus(),
                estimation.getEstimatedTimeMinutes(),
                estimation.getEstimatedReadyAt(),
                estimation.getAlgorithmUsed()
        );
    }
    
    
    @Transactional(readOnly = true)
    public CountdownResponse getOrderCountdown(Long orderId) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Order not found: " + orderId
                                )
                        );

        long remainingSeconds = 0;
        long remainingMinutes = 0;

        if (order.getStatus() == OrderStatus.PLACED
                || order.getStatus() == OrderStatus.IN_PROGRESS) {

            remainingSeconds =
                    Duration.between(
                            LocalDateTime.now(),
                            order.getEstimatedReadyAt()
                    ).getSeconds();

            remainingSeconds =
                    Math.max(0, remainingSeconds);

            remainingMinutes =
                    (long) Math.ceil(
                            remainingSeconds / 60.0
                    );
        }

        return new CountdownResponse(
                order.getId(),
                order.getStatus(),
                order.getEstimatedReadyAt(),
                remainingSeconds,
                remainingMinutes
        );
    }
    
    public OrderResponse updateOrderStatus(
            Long orderId,
            OrderStatus newStatus) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new IllegalArgumentException(
                                        "Order not found: " + orderId
                                )
                        );

        OrderStatus currentStatus = order.getStatus();

        validateStatusTransition(
                currentStatus,
                newStatus
        );

        order.setStatus(newStatus);

        Order savedOrder =
                orderRepository.save(order);

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getRestaurant().getId(),
                savedOrder.getStatus(),
                calculateRemainingEstimatedMinutes(savedOrder),
                savedOrder.getEstimatedReadyAt(),
                ESTIMATION_ALGORITHM
//                "RESOURCE_AWARE"
        );
    }
    
    private void validateStatusTransition(
            OrderStatus currentStatus,
            OrderStatus newStatus) {

        boolean validTransition =
                (currentStatus == OrderStatus.PLACED
                        && (newStatus == OrderStatus.IN_PROGRESS
                        || newStatus == OrderStatus.CANCELLED))

                || (currentStatus == OrderStatus.IN_PROGRESS
                        && (newStatus == OrderStatus.READY
                        || newStatus == OrderStatus.CANCELLED))

                || (currentStatus == OrderStatus.READY
                        && newStatus == OrderStatus.COMPLETED);

        if (!validTransition) {
            throw new IllegalStateException(
                    "Invalid order status transition: "
                            + currentStatus
                            + " -> "
                            + newStatus
            );
        }
    }
    
    private long calculateRemainingEstimatedMinutes(
            Order order) {

        long remainingSeconds =
                Duration.between(
                        LocalDateTime.now(),
                        order.getEstimatedReadyAt()
                ).getSeconds();

        remainingSeconds =
                Math.max(0, remainingSeconds);

        return (long) Math.ceil(
                remainingSeconds / 60.0
        );
    }
    
    @Transactional(readOnly = true)
    public List<EstimationExecutionResponse>
    getEstimationHistory(Long orderId) {

        // Make sure order exists
        orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Order not found: " + orderId
                        )
                );

        List<EstimationExecution> executions =
                estimationExecutionRepository
                        .findByOrderIdOrderByExecutionTimestampDesc(
                                orderId
                        );

        /*return executions.stream()
                .map(this::toEstimationExecutionResponse)
                .collect(Collectors.toList());*/
        
        List<EstimationExecutionResponse> responses = new ArrayList<>();

        for (EstimationExecution execution : executions) {
            responses.add(toEstimationExecutionResponse(execution));
        }
        
        return responses;
    }
    
    private EstimationExecutionResponse
    toEstimationExecutionResponse(
            EstimationExecution execution) {

        return new EstimationExecutionResponse(
                execution.getId(),
                execution.getOrder().getId(),
                execution.getExecutionTimestamp(),
                execution.getElapsedTimeSeconds(),
                execution.getEstimatedTimeMinutes(),
                execution.getOrderStatus(),
                execution.getAlgorithmUsed(),
                execution.getEstimatedReadyAt()
        );
    }
    
}