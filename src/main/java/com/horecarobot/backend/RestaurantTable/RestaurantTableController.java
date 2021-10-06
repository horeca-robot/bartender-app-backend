package com.horecarobot.backend.RestaurantTable;


import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/restaurantTable")
@CrossOrigin(origins = "http://localhost:8081")
public class RestaurantTableController {
    private final RestaurantTableService restaurantTableService;

    @Autowired
    public RestaurantTableController(RestaurantTableService restaurantTableService) {
        this.restaurantTableService = restaurantTableService;
    }

    @GetMapping
    public List<RestaurantTable> getRestaurantTables() {
        return restaurantTableService.getRestaurantTables();
    }

    @PostMapping
    public void createRestaurantTable(@RequestBody RestaurantTable restaurantTable) {
        restaurantTableService.addRestTable(restaurantTable);
    }
}
