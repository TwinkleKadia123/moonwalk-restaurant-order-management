/**
 * 
 */
package com.moonwalk.restaurant.repository;

/**
 * 
 */

import com.moonwalk.restaurant.entity.KitchenResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KitchenResourceRepository
        extends JpaRepository<KitchenResource, Long> {

    List<KitchenResource> findByRestaurantId(Long restaurantId);

    List<KitchenResource> findByRestaurantIdAndType(
            Long restaurantId,
            String type
    );
}