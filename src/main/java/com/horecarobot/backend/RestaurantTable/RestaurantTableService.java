package com.horecarobot.backend.RestaurantTable;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantTableRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RestaurantTableService {
    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public RestaurantTable getTable(UUID id) throws NotFoundException {
        return this.restaurantTableRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot find object"));
    }

    public List<RestaurantTable> getRestaurantTables() {
        return restaurantTableRepository.findAll();
    }

    public void addRestTable(RestaurantTable restaurantTable) {
        restaurantTableRepository.save(restaurantTable);
    }
}

