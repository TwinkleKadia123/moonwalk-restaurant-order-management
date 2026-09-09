/**
 * 
 */
package com.moonwalk.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moonwalk.restaurant.dto.CountdownResponse;
/**
 * 
 */
import com.moonwalk.restaurant.dto.CreateOrderRequest;
import com.moonwalk.restaurant.dto.EstimationExecutionResponse;
import com.moonwalk.restaurant.dto.OrderResponse;
import com.moonwalk.restaurant.entity.OrderStatus;
import com.moonwalk.restaurant.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping
	public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {

		OrderResponse response = orderService.createOrder(request);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping("/{orderId}/countdown")
	public ResponseEntity<CountdownResponse> getOrderCountdown(@PathVariable Long orderId) {

		CountdownResponse response = orderService.getOrderCountdown(orderId);

		return ResponseEntity.ok(response);
	}

	@PatchMapping("/{orderId}/status")
	public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long orderId,
			@RequestParam OrderStatus status) {

		OrderResponse response = orderService.updateOrderStatus(orderId, status);

		return ResponseEntity.ok(response);
	}

	@GetMapping("/{orderId}/estimations")
	public ResponseEntity<List<EstimationExecutionResponse>> getEstimationHistory(@PathVariable Long orderId) {

		List<EstimationExecutionResponse> history = orderService.getEstimationHistory(orderId);

		return ResponseEntity.ok(history);
	}

}