package com.horecarobot.backend.Order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horecarobot.backend.Product.CreateOrderProductDTO;
import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class CreateRestaurantOrderDTO {
    private RestaurantTable table;

    private List<CreateOrderProductDTO> products = new ArrayList<>();
}
