package com.horecarobot.backend.RestaurantTable;

import lombok.Data;

import java.util.UUID;

@Data
public class RestaurantTableDTO {
    private UUID id;
    private Integer tableNumber;
    private Integer xAxis;
    private Integer yAxis;
}
