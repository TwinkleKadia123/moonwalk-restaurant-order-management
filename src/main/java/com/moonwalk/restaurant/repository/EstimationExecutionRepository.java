/**
 * 
 */
package com.moonwalk.restaurant.repository;

/**
 * 
 */
import com.moonwalk.restaurant.entity.EstimationExecution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstimationExecutionRepository
        extends JpaRepository<EstimationExecution, Long> {

    List<EstimationExecution> findByOrderIdOrderByExecutionTimestampDesc(
            Long orderId
    );
}