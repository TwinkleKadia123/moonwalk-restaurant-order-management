package com.moonwalk.config;

import com.moonwalk.restaurant.entity.KitchenResource;
import com.moonwalk.restaurant.entity.MenuItem;
import com.moonwalk.restaurant.entity.Restaurant;
import com.moonwalk.restaurant.repository.KitchenResourceRepository;
import com.moonwalk.restaurant.repository.MenuItemRepository;
import com.moonwalk.restaurant.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initializeData(
            RestaurantRepository restaurantRepository,
            MenuItemRepository menuItemRepository,
            KitchenResourceRepository kitchenResourceRepository) {

        return args -> {

            // Restaurant 1
            Restaurant restaurant1 =
                    restaurantRepository.save(
                            new Restaurant(
                                    null,
                                    "MoonWalk Space Restaurant"));

            // Restaurant 2
            Restaurant restaurant2 =
                    restaurantRepository.save(
                            new Restaurant(
                                    null,
                                    "Galaxy Bites"));

            // -------------------------
            // Restaurant 1 Menu
            // -------------------------

            menuItemRepository.save(
                    new MenuItem(
                            null,
                            "Space Pizza",
                            15,
                            "OVEN",
                            restaurant1));

            menuItemRepository.save(
                    new MenuItem(
                            null,
                            "Moon Burger",
                            10,
                            "GRILL",
                            restaurant1));

            menuItemRepository.save(
                    new MenuItem(
                            null,
                            "Zero Gravity Fries",
                            5,
                            "FRYER",
                            restaurant1));

            // -------------------------
            // Restaurant 1 Kitchen
            // -------------------------

            kitchenResourceRepository.save(
                    new KitchenResource(
                            null,
                            "OVEN-1",
                            "OVEN",
                            1,
                            restaurant1));

            kitchenResourceRepository.save(
                    new KitchenResource(
                            null,
                            "GRILL-1",
                            "GRILL",
                            1,
                            restaurant1));

            kitchenResourceRepository.save(
                    new KitchenResource(
                            null,
                            "FRYER-1",
                            "FRYER",
                            1,
                            restaurant1));


            // -------------------------
            // Restaurant 2 Menu
            // -------------------------

            menuItemRepository.save(
                    new MenuItem(
                            null,
                            "Galaxy Pasta",
                            20,
                            "OVEN",
                            restaurant2));

            menuItemRepository.save(
                    new MenuItem(
                            null,
                            "Cosmic Sandwich",
                            8,
                            "GRILL",
                            restaurant2));

            menuItemRepository.save(
                    new MenuItem(
                            null,
                            "Meteor Fries",
                            6,
                            "FRYER",
                            restaurant2));

            // -------------------------
            // Restaurant 2 Kitchen
            // -------------------------

            kitchenResourceRepository.save(
                    new KitchenResource(
                            null,
                            "OVEN-1",
                            "OVEN",
                            1,
                            restaurant2));

            kitchenResourceRepository.save(
                    new KitchenResource(
                            null,
                            "GRILL-1",
                            "GRILL",
                            1,
                            restaurant2));

            kitchenResourceRepository.save(
                    new KitchenResource(
                            null,
                            "FRYER-1",
                            "FRYER",
                            1,
                            restaurant2));

            System.out.println(
                    "Multi-restaurant sample data initialized successfully.");
        };
    }
}