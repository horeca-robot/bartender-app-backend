package com.horecarobot.backend.Order;

import com.horecarobot.backend.Product.CreateOrderProductDTO;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateRestaurantOrderDTO {
    private RestaurantTable table;

    private List<CreateOrderProductDTO> products = new ArrayList<>();
}
