package com.horecarobot.backend.RestaurantTable;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public List<RestaurantTable> getRestaurantTables() {
        return restaurantTableRepository.findAll();
    }

    public void addRestTable(RestaurantTable restaurantTable) {
        restaurantTableRepository.save(restaurantTable);
    }
}

