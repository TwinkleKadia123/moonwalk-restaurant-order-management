/**
 * 
 */
package com.moonwalk.restaurant.dto;

/**
 * 
 */
import com.moonwalk.restaurant.entity.OrderStatus;

import java.time.LocalDateTime;

public class EstimationExecutionResponse {

    private Long executionId;
    private Long orderId;
    private LocalDateTime executionTimestamp;
    private long elapsedTimeSeconds;
    private long estimatedTimeMinutes;
    private OrderStatus orderStatus;
    private String algorithmUsed;
    private LocalDateTime estimatedReadyAt;

    public EstimationExecutionResponse() {
    }

    public EstimationExecutionResponse(
            Long executionId,
            Long orderId,
            LocalDateTime executionTimestamp,
            long elapsedTimeSeconds,
            long estimatedTimeMinutes,
            OrderStatus orderStatus,
            String algorithmUsed,
            LocalDateTime estimatedReadyAt) {

        this.executionId = executionId;
        this.orderId = orderId;
        this.executionTimestamp = executionTimestamp;
        this.elapsedTimeSeconds = elapsedTimeSeconds;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.orderStatus = orderStatus;
        this.algorithmUsed = algorithmUsed;
        this.estimatedReadyAt = estimatedReadyAt;
    }

    public Long getExecutionId() {
        return executionId;
    }

    public void setExecutionId(Long executionId) {
        this.executionId = executionId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getExecutionTimestamp() {
        return executionTimestamp;
    }

    public void setExecutionTimestamp(
            LocalDateTime executionTimestamp) {
        this.executionTimestamp = executionTimestamp;
    }

    public long getElapsedTimeSeconds() {
        return elapsedTimeSeconds;
    }

    public void setElapsedTimeSeconds(long elapsedTimeSeconds) {
        this.elapsedTimeSeconds = elapsedTimeSeconds;
    }

    public long getEstimatedTimeMinutes() {
        return estimatedTimeMinutes;
    }

    public void setEstimatedTimeMinutes(
            long estimatedTimeMinutes) {
        this.estimatedTimeMinutes = estimatedTimeMinutes;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAlgorithmUsed() {
        return algorithmUsed;
    }

    public void setAlgorithmUsed(String algorithmUsed) {
        this.algorithmUsed = algorithmUsed;
    }

    public LocalDateTime getEstimatedReadyAt() {
        return estimatedReadyAt;
    }

    public void setEstimatedReadyAt(
            LocalDateTime estimatedReadyAt) {
        this.estimatedReadyAt = estimatedReadyAt;
    }
}