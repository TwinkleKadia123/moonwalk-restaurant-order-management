package com.moonwalk.restaurant.service;

import com.moonwalk.restaurant.entity.KitchenResource;
import com.moonwalk.restaurant.entity.KitchenResourceAvailability;
import com.moonwalk.restaurant.entity.Order;
import com.moonwalk.restaurant.entity.OrderItem;
import com.moonwalk.restaurant.entity.OrderStatus;
import com.moonwalk.restaurant.repository.KitchenResourceRepository;
import com.moonwalk.restaurant.repository.OrderRepository;
import com.moonwalk.restaurant.strategy.KitchenState;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class KitchenService {

    private final KitchenResourceRepository kitchenResourceRepository;
    private final OrderRepository orderRepository;

    public KitchenService(
            KitchenResourceRepository kitchenResourceRepository,
            OrderRepository orderRepository) {

        this.kitchenResourceRepository = kitchenResourceRepository;
        this.orderRepository = orderRepository;
    }

    public KitchenState getKitchenState(Long restaurantId) {

        // Get all kitchen resources for the restaurant
        List<KitchenResource> resources =
                kitchenResourceRepository.findByRestaurantId(restaurantId);

        // Get currently active orders
        List<Order> activeOrders =
                orderRepository.findByRestaurantId(restaurantId)
                        .stream()
                        .filter(this::isActiveOrder)
                        .toList();

        // Calculate total capacity for each resource type
        Map<String, Integer> capacityByResource =
                resources.stream()
                        .collect(Collectors.groupingBy(
                                KitchenResource::getType,
                                Collectors.summingInt(
                                        KitchenResource::getCapacity
                                )
                        ));

        // Calculate current backlog
        Map<String, Integer> backlogByResource =
                calculateBacklog(
                        activeOrders,
                        capacityByResource
                );

        KitchenState kitchenState = new KitchenState();

        // Build kitchen state
        for (Map.Entry<String, Integer> entry
                : capacityByResource.entrySet()) {

            String resourceType = entry.getKey();

            int totalCapacity = entry.getValue();

            int waitingTime =
                    backlogByResource.getOrDefault(
                            resourceType,
                            0
                    );

            KitchenResourceAvailability availability =
                    new KitchenResourceAvailability(
                            resourceType,
                            totalCapacity,
                            waitingTime
                    );

            kitchenState.addResource(availability);
        }

        return kitchenState;
    }

    private Map<String, Integer> calculateBacklog(
            List<Order> activeOrders,
            Map<String, Integer> capacityByResource) {

        /*
         * Calculate total preparation workload
         * for each kitchen resource type.
         */
        Map<String, Integer> totalWorkByResource =
                activeOrders.stream()
                        .flatMap(order ->
                                order.getOrderItems().stream())
                        .collect(Collectors.groupingBy(
                                item -> item.getMenuItem()
                                        .getRequiredResourceType(),

                                Collectors.summingInt(
                                        this::calculatePreparationTime
                                )
                        ));

        /*
         * Convert workload into effective waiting time
         * based on available resource capacity.
         */
        return totalWorkByResource.entrySet()
                .stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,

                        entry -> calculateEffectiveBacklog(
                                entry.getValue(),

                                capacityByResource.getOrDefault(
                                        entry.getKey(),
                                        1
                                )
                        )
                ));
    }

    private int calculateEffectiveBacklog(
            int totalWorkMinutes,
            int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException(
                    "Kitchen resource capacity must be greater than zero"
            );
        }

        return (int) Math.ceil(
                (double) totalWorkMinutes / capacity
        );
    }

    private int calculatePreparationTime(OrderItem item) {

        return item.getMenuItem()
                .getPreparationTimeMinutes()
                * item.getQuantity();
    }

    private boolean isActiveOrder(Order order) {

        return order.getStatus() == OrderStatus.PLACED
                || order.getStatus() == OrderStatus.IN_PROGRESS;
    }
}