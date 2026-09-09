/**
 * 
 */
package com.moonwalk.restaurant.dto;

/**
 * 
 */
import com.moonwalk.restaurant.entity.OrderStatus;

import java.time.LocalDateTime;

public class OrderResponse {

    private Long orderId;
    private Long restaurantId;
    private OrderStatus status;
    private long estimatedTimeMinutes;
    private LocalDateTime estimatedReadyAt;
    private String algorithmUsed;

    public OrderResponse() {
    }

    public OrderResponse(
            Long orderId,
            Long restaurantId,
            OrderStatus status,
            long estimatedTimeMinutes,
            LocalDateTime estimatedReadyAt,
            String algorithmUsed) {

        this.orderId = orderId;
        this.restaurantId = restaurantId;
        this.status = status;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.estimatedReadyAt = estimatedReadyAt;
        this.algorithmUsed = algorithmUsed;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public long getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public void setEstimatedTimeMinutes(long estimatedTimeMinutes) {
        this.estimatedTimeMinutes = estimatedTimeMinutes;
    }

    public LocalDateTime getEstimatedReadyAt() {
        return estimatedReadyAt;
    }

    public void setEstimatedReadyAt(LocalDateTime estimatedReadyAt) {
        this.estimatedReadyAt = estimatedReadyAt;
    }

    public String getAlgorithmUsed() {
        return algorithmUsed;
    }

    public void setAlgorithmUsed(String algorithmUsed) {
        this.algorithmUsed = algorithmUsed;
    }
}