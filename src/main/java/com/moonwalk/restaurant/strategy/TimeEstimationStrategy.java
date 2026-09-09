/**
 * 
 */
package com.moonwalk.restaurant.strategy;

/**
 * 
 */
import com.moonwalk.restaurant.dto.EstimationResult;
import com.moonwalk.restaurant.entity.Order;

public interface TimeEstimationStrategy {

    EstimationResult estimate(
            Order order,
            KitchenState kitchenState
    );
}