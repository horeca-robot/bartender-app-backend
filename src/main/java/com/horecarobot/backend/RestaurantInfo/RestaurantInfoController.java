package com.horecarobot.backend.RestaurantInfo;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/v1/restaurantInfo")
@CrossOrigin(origins = {"http://localhost:8081", "https://rigs.bryanaafjes.nl", "https://rigs.bryanaafjes.nl:444"})
public class RestaurantInfoController {
    private final RestaurantInfoService restaurantInfoService;

    @Autowired
    public RestaurantInfoController(RestaurantInfoService restaurantInfoService){
        this.restaurantInfoService = restaurantInfoService;
    }

    @GetMapping
    public RestaurantInfo GetAllRestaurantInfo() throws NotFoundException {
        return this.restaurantInfoService.getAllRestaurantInfo();
    }
}
