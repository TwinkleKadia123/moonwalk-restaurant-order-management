/**
 * 
 */
package com.moonwalk.restaurant.repository;

/**
 * 
 */

import com.moonwalk.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	 boolean existsByName(String name);
}