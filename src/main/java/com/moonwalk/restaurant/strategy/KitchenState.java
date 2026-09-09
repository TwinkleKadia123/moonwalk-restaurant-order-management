/**
 * 
 */
package com.moonwalk.restaurant.strategy;

/**
 * 
 */
import com.moonwalk.restaurant.entity.KitchenResourceAvailability;

import java.util.HashMap;
import java.util.Map;

public class KitchenState {

    private Map<String, KitchenResourceAvailability> resources =
            new HashMap<>();

    public KitchenState() {
    }

    public void addResource(
            KitchenResourceAvailability availability) {

        resources.put(
                availability.getResourceType(),
                availability
        );
    }

    public KitchenResourceAvailability getResource(
            String resourceType) {

        return resources.get(resourceType);
    }

    public Map<String, KitchenResourceAvailability> getResources() {
        return resources;
    }
}