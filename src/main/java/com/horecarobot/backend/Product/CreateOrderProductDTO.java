package com.horecarobot.backend.Product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import edu.fontys.horecarobot.databaselibrary.models.IngredientProduct;
import edu.fontys.horecarobot.databaselibrary.models.Tag;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CreateOrderProductDTO {
    private UUID id;
    private int count;
}
