package com.horecarobot.backend.Order;

import com.horecarobot.backend.Product.CreateOrderProductDTO;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CreateRestaurantOrderDTO {
    private UUID id;
    private double subTotal;
    private boolean paid = false;
    private Date createdAt;
    private RestaurantTable table;

    private List<CreateOrderProductDTO> products = new ArrayList<>();
}
