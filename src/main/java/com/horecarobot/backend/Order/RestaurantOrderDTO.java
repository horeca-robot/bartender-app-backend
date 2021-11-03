package com.horecarobot.backend.Order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class RestaurantOrderDTO {
    private UUID id;
    private double subTotal;
    private boolean paid;
    private Date createdAt;
    private RestaurantTable table;

    @JsonIgnoreProperties("restaurantOrder")
    private List<ProductOrder> productOrders = new ArrayList<>();
}
