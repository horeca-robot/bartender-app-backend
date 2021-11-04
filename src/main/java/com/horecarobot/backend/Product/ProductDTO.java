package com.horecarobot.backend.Product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import edu.fontys.horecarobot.databaselibrary.models.Tag;
import edu.fontys.horecarobot.databaselibrary.models.IngredientProduct;
import lombok.Data;

@Data
public class ProductDTO {
    private UUID id;
    private String name;
    private String image;
    private double price;
    private double discountPrice;
    private String description;
    private boolean containsAlcohol;
    private List<Tag> tags = new ArrayList<>();

    @JsonIgnoreProperties("product")
    private List<IngredientProduct> ingredients;
}
