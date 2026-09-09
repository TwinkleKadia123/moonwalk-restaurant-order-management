/**
 * 
 */
package com.moonwalk.restaurant.repository;

/**
 * 
 */
import com.moonwalk.restaurant.entity.Order;
import com.moonwalk.restaurant.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByRestaurantId(Long restaurantId);

    List<Order> findByRestaurantIdAndStatus(
            Long restaurantId,
            OrderStatus status
    );
}