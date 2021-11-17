package com.horecarobot.backend.RestaurantTable;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantTableRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
<<<<<<< HEAD
=======

>>>>>>> develop
import java.util.List;
import java.util.UUID;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public RestaurantTable getRestaurantTable(UUID id) throws NotFoundException {
        return this.restaurantTableRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find object"));
    }

    public List<RestaurantTable> getRestaurantTables() {
        return restaurantTableRepository.findAll();
    }

    public void addRestaurantTable(RestaurantTable restaurantTable) {
        restaurantTableRepository.save(restaurantTable);
    }
}

