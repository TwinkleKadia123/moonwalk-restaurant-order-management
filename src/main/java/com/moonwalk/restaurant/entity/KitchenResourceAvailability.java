/**
 * 
 */
package com.moonwalk.restaurant.entity;

/**
 * 
 */
public class KitchenResourceAvailability {

    private String resourceType;

    private int availableCapacity;

    private int waitingTimeMinutes;

    public KitchenResourceAvailability() {
    }

    public KitchenResourceAvailability(
            String resourceType,
            int availableCapacity,
            int waitingTimeMinutes) {

        this.resourceType = resourceType;
        this.availableCapacity = availableCapacity;
        this.waitingTimeMinutes = waitingTimeMinutes;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public int getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(int availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public int getWaitingTimeMinutes() {
        return waitingTimeMinutes;
    }

    public void setWaitingTimeMinutes(int waitingTimeMinutes) {
        this.waitingTimeMinutes = waitingTimeMinutes;
    }
}