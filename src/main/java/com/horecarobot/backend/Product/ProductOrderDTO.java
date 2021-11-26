package com.horecarobot.backend.Product;

import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ProductOrderDTO {
    private UUID id;
    private OrderStatus orderStatus;
    private ProductDTO product;
    private List<ProductDTO> byProducts = new ArrayList<>();
}
