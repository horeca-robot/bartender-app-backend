package com.horecarobot.backend.Order;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import java.util.Comparator;

public class OrderCompare implements Comparator<RestaurantOrder> {

    @Override
    public int compare(RestaurantOrder restaurantOrderDTO, RestaurantOrder t1) {
        if(restaurantOrderDTO.isOrderDone() && !t1.isOrderDone()){
            return  1;
        } else {
            if(restaurantOrderDTO.isOrderDone() && t1.isOrderDone()){
                return 0;
            }
            return -1;
        }
    }
}
