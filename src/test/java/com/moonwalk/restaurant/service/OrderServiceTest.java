/**
 * 
 */
package com.moonwalk.restaurant.service;

/**
 * 
 */
import com.moonwalk.restaurant.repository.EstimationExecutionRepository;
import com.moonwalk.restaurant.repository.MenuItemRepository;
import com.moonwalk.restaurant.repository.OrderRepository;
import com.moonwalk.restaurant.repository.RestaurantRepository;
import com.moonwalk.restaurant.strategy.TimeEstimationStrategy;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.moonwalk.restaurant.dto.CreateOrderItemRequest;
import com.moonwalk.restaurant.dto.CreateOrderRequest;
import org.junit.jupiter.api.Test;
import com.moonwalk.restaurant.entity.Restaurant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.moonwalk.restaurant.dto.EstimationResult;
import com.moonwalk.restaurant.dto.OrderResponse;
import com.moonwalk.restaurant.entity.KitchenResourceAvailability;
import com.moonwalk.restaurant.entity.MenuItem;
import com.moonwalk.restaurant.entity.Order;
import com.moonwalk.restaurant.entity.OrderStatus;
import com.moonwalk.restaurant.strategy.KitchenState;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private MenuItemRepository menuItemRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private EstimationExecutionRepository estimationExecutionRepository;

    @Mock
    private KitchenService kitchenService;

    @Mock
    private TimeEstimationStrategy estimationStrategy;

    @InjectMocks
    private OrderService orderService;
    
    @Test
    void shouldThrowExceptionWhenRestaurantDoesNotExist() {

        CreateOrderRequest request =
                new CreateOrderRequest(
                        999L,
                        List.of(
                                new CreateOrderItemRequest(
                                        1L,
                                        1
                                )
                        )
                );

        when(restaurantRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(request)
        );
    }
    
   
    @Test
    void shouldThrowExceptionWhenMenuItemDoesNotExist() {

        Restaurant restaurant =
                new Restaurant(
                        1L,
                        "MoonWalk Space Restaurant"
                );

        CreateOrderRequest request =
                new CreateOrderRequest(
                        1L,
                        List.of(
                                new CreateOrderItemRequest(
                                        999L,
                                        1
                                )
                        )
                );

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        when(menuItemRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                IllegalArgumentException.class,
                () -> orderService.createOrder(request)
        );
    }
    
    @Test
    void shouldCreateOrderSuccessfully() {

        Restaurant restaurant =
                new Restaurant(
                        1L,
                        "MoonWalk Space Restaurant"
                );

        MenuItem pizza =
                new MenuItem(
                        1L,
                        "Space Pizza",
                        15,
                        "OVEN",
                        restaurant
                );

        CreateOrderRequest request =
                new CreateOrderRequest(
                        1L,
                        List.of(
                                new CreateOrderItemRequest(
                                        1L,
                                        1
                                )
                        )
                );

        KitchenState kitchenState =
                new KitchenState();

        kitchenState.addResource(
                new KitchenResourceAvailability(
                        "OVEN",
                        2,
                        0
                )
        );

        LocalDateTime estimatedReadyAt =
                LocalDateTime.now().plusMinutes(15);

        EstimationResult estimationResult =
                new EstimationResult(
                        15,
                        estimatedReadyAt,
                        "RESOURCE_AWARE"
                );

        when(restaurantRepository.findById(1L))
                .thenReturn(Optional.of(restaurant));

        when(menuItemRepository.findById(1L))
                .thenReturn(Optional.of(pizza));

        when(kitchenService.getKitchenState(1L))
                .thenReturn(kitchenState);

        when(estimationStrategy.estimate(
                org.mockito.ArgumentMatchers.any(Order.class),
                org.mockito.ArgumentMatchers.eq(kitchenState)
        )).thenReturn(estimationResult);

        when(orderRepository.save(
                org.mockito.ArgumentMatchers.any(Order.class)
        )).thenAnswer(invocation -> {

            Order order = invocation.getArgument(0);
            order.setId(1L);
            return order;
        });

        OrderResponse response =
                orderService.createOrder(request);

        assertEquals(1L, response.getOrderId());
        assertEquals(1L, response.getRestaurantId());
        assertEquals(
                OrderStatus.PLACED,
                response.getStatus()
        );
        assertEquals(
                15,
                response.getEstimatedTimeMinutes()
        );
        assertEquals(
                "RESOURCE_AWARE",
                response.getAlgorithmUsed()
        );

        verify(orderRepository).save(
                org.mockito.ArgumentMatchers.any(Order.class)
        );

        verify(estimationExecutionRepository).save(
                org.mockito.ArgumentMatchers.any(
                        com.moonwalk.restaurant.entity.EstimationExecution.class
                )
        );
    }
    
    @Test
    void shouldRejectInvalidStatusTransition() {

        Restaurant restaurant =
                new Restaurant(
                        1L,
                        "MoonWalk Space Restaurant"
                );

        Order order = new Order();
        order.setId(1L);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.PLACED);
        order.setEstimatedReadyAt(
                LocalDateTime.now().plusMinutes(15)
        );

        when(orderRepository.findById(1L))
                .thenReturn(Optional.of(order));

        assertThrows(
                IllegalStateException.class,
                () -> orderService.updateOrderStatus(
                        1L,
                        OrderStatus.COMPLETED
                )
        );
    }
    
}