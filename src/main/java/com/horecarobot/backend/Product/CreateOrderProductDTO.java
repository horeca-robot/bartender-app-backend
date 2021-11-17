package com.horecarobot.backend.Product;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderProductDTO {
    private UUID id;
    private int count;
}
