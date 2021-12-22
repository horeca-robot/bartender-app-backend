package com.horecarobot.backend.RestaurantInfo;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantInfoRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantInfoService {
    private RestaurantInfoRepository restaurantInfoRepository;

    @Autowired
    public RestaurantInfoService(RestaurantInfoRepository restaurantInfoRepository) {
        this.restaurantInfoRepository = restaurantInfoRepository;

    }

    public RestaurantInfo getAllRestaurantInfo() throws NotFoundException {
        var restaurantInfo = restaurantInfoRepository.getInfo();

        if(restaurantInfo.isEmpty())
        {
            throw new NotFoundException("Info not found");
        }
        return restaurantInfo.get();
    }
}
