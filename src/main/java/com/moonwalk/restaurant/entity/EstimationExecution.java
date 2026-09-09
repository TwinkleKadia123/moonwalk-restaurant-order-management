/**
 * 
 */
package com.moonwalk.restaurant.entity;

/**
 * 
 */
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "estimation_executions")
public class EstimationExecution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private LocalDateTime executionTimestamp;

    @Column(nullable = false)
    private long elapsedTimeSeconds;

    @Column(nullable = false)
    private long estimatedTimeMinutes;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private String algorithmUsed;

    private LocalDateTime estimatedReadyAt;

    public EstimationExecution() {
    }

    public EstimationExecution(
            Long id,
            Order order,
            LocalDateTime executionTimestamp,
            long elapsedTimeSeconds,
            long estimatedTimeMinutes,
            OrderStatus orderStatus,
            String algorithmUsed,
            LocalDateTime estimatedReadyAt) {

        this.id = id;
        this.order = order;
        this.executionTimestamp = executionTimestamp;
        this.elapsedTimeSeconds = elapsedTimeSeconds;
        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.orderStatus = orderStatus;
        this.algorithmUsed = algorithmUsed;
        this.estimatedReadyAt = estimatedReadyAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public LocalDateTime getExecutionTimestamp() {
        return executionTimestamp;
    }

    public void setExecutionTimestamp(LocalDateTime executionTimestamp) {
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

    public void setEstimatedTimeMinutes(long estimatedTimeMinutes) {
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

    public void setEstimatedReadyAt(LocalDateTime estimatedReadyAt) {
        this.estimatedReadyAt = estimatedReadyAt;
    }
}