package com.horecarobot.backend.Order;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import java.util.Comparator;

public class OrderCompareDate implements Comparator<RestaurantOrder> {
    @Override
    public int compare(RestaurantOrder o1, RestaurantOrder o2) {
        return o2.getCreatedAt().compareTo(o1.getCreatedAt());
    }
}
