/**
 * 
 */
package com.moonwalk.restaurant.entity;

/**
 * 
 */
import jakarta.persistence.*;

@Entity
@Table(name = "menu_items")
public class MenuItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int preparationTimeMinutes;

    @Column(nullable = false)
    private String requiredResourceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public MenuItem() {
    }

    public MenuItem(Long id, String name, int preparationTimeMinutes,
                    String requiredResourceType, Restaurant restaurant) {
        this.id = id;
        this.name = name;
        this.preparationTimeMinutes = preparationTimeMinutes;
        this.requiredResourceType = requiredResourceType;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreparationTimeMinutes() {
        return preparationTimeMinutes;
    }

    public void setPreparationTimeMinutes(int preparationTimeMinutes) {
        this.preparationTimeMinutes = preparationTimeMinutes;
    }

    public String getRequiredResourceType() {
        return requiredResourceType;
    }

    public void setRequiredResourceType(String requiredResourceType) {
        this.requiredResourceType = requiredResourceType;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}