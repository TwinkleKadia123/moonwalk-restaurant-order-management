/**
 * 
 */
package com.moonwalk.restaurant.entity;

/**
 * 
 */

	import jakarta.persistence.*;

	import java.time.LocalDateTime;
	import java.util.ArrayList;
	import java.util.List;

	@Entity
	@Table(name = "orders")
	public class Order {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "restaurant_id", nullable = false)
	    private Restaurant restaurant;

	    @Enumerated(EnumType.STRING)
	    @Column(nullable = false)
	    private OrderStatus status;

	    @Column(nullable = false)
	    private LocalDateTime createdAt;

	    private LocalDateTime estimatedReadyAt;

	    @OneToMany(
	            mappedBy = "order",
	            cascade = CascadeType.ALL,
	            orphanRemoval = true
	    )
	    private List<OrderItem> orderItems = new ArrayList<>();

	    public Order() {
	    }

	    public Order(Long id, Restaurant restaurant, OrderStatus status,
	                 LocalDateTime createdAt, LocalDateTime estimatedReadyAt) {
	        this.id = id;
	        this.restaurant = restaurant;
	        this.status = status;
	        this.createdAt = createdAt;
	        this.estimatedReadyAt = estimatedReadyAt;
	    }

	    public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Restaurant getRestaurant() {
	        return restaurant;
	    }

	    public void setRestaurant(Restaurant restaurant) {
	        this.restaurant = restaurant;
	    }

	    public OrderStatus getStatus() {
	        return status;
	    }

	    public void setStatus(OrderStatus status) {
	        this.status = status;
	    }

	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }

	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }

	    public LocalDateTime getEstimatedReadyAt() {
	        return estimatedReadyAt;
	    }

	    public void setEstimatedReadyAt(LocalDateTime estimatedReadyAt) {
	        this.estimatedReadyAt = estimatedReadyAt;
	    }

	    public List<OrderItem> getOrderItems() {
	        return orderItems;
	    }

	    public void setOrderItems(List<OrderItem> orderItems) {
	        this.orderItems = orderItems;
	    }

	    public void addOrderItem(OrderItem orderItem) {
	        orderItems.add(orderItem);
	        orderItem.setOrder(this);
	    }

	    public void removeOrderItem(OrderItem orderItem) {
	        orderItems.remove(orderItem);
	        orderItem.setOrder(null);
	    }
	}