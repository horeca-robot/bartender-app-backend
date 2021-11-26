package com.horecarobot.backend.Product;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;

import java.util.List;
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

    public Product getProduct(UUID productID) throws NotFoundException {
        return this.productRepository.findById(productID).orElseThrow(() -> new NotFoundException("Cannot find object"));
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void deleteProduct(UUID productID) throws NotFoundException {
        productRepository.delete(this.getProduct(productID));
    }

}
