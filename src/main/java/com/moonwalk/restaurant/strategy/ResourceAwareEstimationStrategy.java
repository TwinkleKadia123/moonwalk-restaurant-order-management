/**
 * 
 */
package com.moonwalk.restaurant.strategy;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import com.moonwalk.restaurant.dto.EstimationResult;
import com.moonwalk.restaurant.entity.KitchenResourceAvailability;
import com.moonwalk.restaurant.entity.Order;
import com.moonwalk.restaurant.entity.OrderItem;

@Component
public class ResourceAwareEstimationStrategy implements TimeEstimationStrategy {

	private static final String ESTIMATION_ALGORITHM = "RESOURCE_AWARE";

	@Override
	public EstimationResult estimate(Order order, KitchenState kitchenState) {

		long maximumCompletionTime = 0;

		for (OrderItem orderItem : order.getOrderItems()) {

			int preparationTime = orderItem.getMenuItem().getPreparationTimeMinutes();

			String resourceType = orderItem.getMenuItem().getRequiredResourceType();

			KitchenResourceAvailability resource = kitchenState.getResource(resourceType);

			if (resource == null) {
				throw new IllegalStateException("Required kitchen resource not available: " + resourceType);
			}

			int waitingTime = resource.getWaitingTimeMinutes();

			long itemCompletionTime = waitingTime + ((long) preparationTime * orderItem.getQuantity());

			maximumCompletionTime = Math.max(maximumCompletionTime, itemCompletionTime);
		}

		LocalDateTime estimatedReadyAt = LocalDateTime.now().plusMinutes(maximumCompletionTime);

		return new EstimationResult(maximumCompletionTime, estimatedReadyAt, ESTIMATION_ALGORITHM
//                "RESOURCE_AWARE"
		);
	}
}