package com.horecarobot.backend.Product;


import edu.fontys.horecarobot.databaselibrary.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/product")
@CrossOrigin(origins = "http://localhost:8081")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Iterable<Product> getAllProducts(){
        return this.productService.getAllProducts();
    }

    @GetMapping(path ="/{id}")
    public Optional<Product> getProduct(@PathVariable("id") UUID productID) { return this.productService.getProduct(productID); }

//    TEMP METHOD
    @PostMapping
    public void createProduct(@RequestBody Product product){
        productService.addProduct(product);
    }
}
