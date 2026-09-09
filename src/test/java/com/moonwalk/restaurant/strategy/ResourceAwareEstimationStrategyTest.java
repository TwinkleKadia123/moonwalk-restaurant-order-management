/**
 * 
 */
package com.moonwalk.restaurant.strategy;

/**
 * 
 */
import com.moonwalk.restaurant.dto.EstimationResult;
import com.moonwalk.restaurant.entity.KitchenResourceAvailability;
import com.moonwalk.restaurant.entity.MenuItem;
import com.moonwalk.restaurant.entity.Order;
import com.moonwalk.restaurant.entity.OrderItem;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ResourceAwareEstimationStrategyTest {

    @Test
    void shouldCalculatePreparationTimeWhenThereIsNoBacklog() {

        MenuItem pizza =
                new MenuItem(
                        1L,
                        "Space Pizza",
                        15,
                        "OVEN",
                        null
                );

        Order order = new Order();

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(pizza);
        orderItem.setQuantity(1);

        order.addOrderItem(orderItem);

        KitchenState kitchenState = new KitchenState();

        kitchenState.addResource(
                new KitchenResourceAvailability(
                        "OVEN",
                        2,
                        0
                )
        );

        ResourceAwareEstimationStrategy strategy =
                new ResourceAwareEstimationStrategy();

        EstimationResult result =
                strategy.estimate(order, kitchenState);

        assertEquals(
                15,
                result.getEstimatedTimeMinutes()
        );
    }
    
    @Test
    void shouldCalculatePreparationTimeForMultipleQuantity() {

        MenuItem pizza =
                new MenuItem(
                        1L,
                        "Space Pizza",
                        15,
                        "OVEN",
                        null
                );

        Order order = new Order();

        OrderItem orderItem = new OrderItem();
        orderItem.setMenuItem(pizza);
        orderItem.setQuantity(2);

        order.addOrderItem(orderItem);

        KitchenState kitchenState = new KitchenState();

        kitchenState.addResource(
                new KitchenResourceAvailability(
                        "OVEN",
                        2,
                        0
                )
        );

        ResourceAwareEstimationStrategy strategy =
                new ResourceAwareEstimationStrategy();

        EstimationResult result =
                strategy.estimate(order, kitchenState);

        assertEquals(
                30,
                result.getEstimatedTimeMinutes()
        );
    } 
    
}