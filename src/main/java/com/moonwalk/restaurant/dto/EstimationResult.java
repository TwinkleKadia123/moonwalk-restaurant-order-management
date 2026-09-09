/**
 * 
 */
package com.moonwalk.restaurant.dto;

/**
 * 
 */
import java.time.LocalDateTime;

public class EstimationResult {

    private long estimatedTimeMinutes;

    private LocalDateTime estimatedReadyAt;

    private String algorithmUsed;

    public EstimationResult() {
    }

    public EstimationResult(
            long estimatedTimeMinutes,
            LocalDateTime estimatedReadyAt,
            String algorithmUsed) {

        this.estimatedTimeMinutes = estimatedTimeMinutes;
        this.estimatedReadyAt = estimatedReadyAt;
        this.algorithmUsed = algorithmUsed;
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