package com.horecarobot.backend.Product;


import edu.fontys.horecarobot.databaselibrary.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/product")
public class ProductController {
    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public Iterable<Product> getAllProducts(){
        return this.productService.getAllProducts();
    }
    @PostMapping
    public void createProduct(@RequestBody Product product){
        productService.addProduct(product);
    }
}
