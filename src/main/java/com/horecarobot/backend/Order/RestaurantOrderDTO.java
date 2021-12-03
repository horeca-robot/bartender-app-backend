package com.horecarobot.backend.Order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.horecarobot.backend.Product.ProductOrderDTO;
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
    private boolean paid = false;
    private Date createdAt;
    private RestaurantTable table;
    private boolean orderDone;

    @JsonIgnoreProperties("order")
    private List<ProductOrderDTO> productOrders = new ArrayList<>();
}
