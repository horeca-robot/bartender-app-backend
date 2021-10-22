package com.horecarobot.backend.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(UUID productID) {
        return  productRepository.findById(productID);
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }
}
