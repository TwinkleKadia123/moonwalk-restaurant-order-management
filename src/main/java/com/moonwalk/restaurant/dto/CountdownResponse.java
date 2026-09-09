package com.moonwalk.restaurant.dto;

import com.moonwalk.restaurant.entity.OrderStatus;

import java.time.LocalDateTime;

public class CountdownResponse {

    private Long orderId;
    private OrderStatus status;
    private LocalDateTime estimatedReadyAt;
    private long remainingTimeSeconds;
    private long remainingTimeMinutes;

    public CountdownResponse() {
    }

    public CountdownResponse(
            Long orderId,
            OrderStatus status,
            LocalDateTime estimatedReadyAt,
            long remainingTimeSeconds,
            long remainingTimeMinutes) {

        this.orderId = orderId;
        this.status = status;
        this.estimatedReadyAt = estimatedReadyAt;
        this.remainingTimeSeconds = remainingTimeSeconds;
        this.remainingTimeMinutes = remainingTimeMinutes;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDateTime getEstimatedReadyAt() {
        return estimatedReadyAt;
    }

    public void setEstimatedReadyAt(LocalDateTime estimatedReadyAt) {
        this.estimatedReadyAt = estimatedReadyAt;
    }

    public long getRemainingTimeSeconds() {
        return remainingTimeSeconds;
    }

    public void setRemainingTimeSeconds(long remainingTimeSeconds) {
        this.remainingTimeSeconds = remainingTimeSeconds;
    }

    public long getRemainingTimeMinutes() {
        return remainingTimeMinutes;
    }

    public void setRemainingTimeMinutes(long remainingTimeMinutes) {
        this.remainingTimeMinutes = remainingTimeMinutes;
    }
}