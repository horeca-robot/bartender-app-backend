package com.horecarobot.backend;

import com.horecarobot.backend.Product.ProductService;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ProductTests {
    private ProductService productService;
    private ProductRepository productRepository;

    @Autowired
    public ProductTests(ProductService productService, ProductRepository productRepository) {
        this.productService = productService;
        this.productRepository = productRepository;
    }

    @Test
    public void Should_Add_Product() throws Exception {
        //Arrange
        UUID uuid = UUID.randomUUID();
        Product product = new Product(uuid, "Cola", "imgPath", 1.50, 0, "This is the original Coca Cola!", false, null, null);
        Product product2 = new Product(uuid, "Cola", "imgPath", 1.50, 0, "This is the original Coca Cola!", false, null, null);

        //Act
        productService.addProduct(product2);

        assertThat(productRepository.findById(uuid)).usingRecursiveComparison().isEqualTo(product);
    }

    @Test
    public void Should_Get_All_Products() {
        //Arrange
        Product product1 = new Product(UUID.randomUUID(), "Cola", "imgPath", 1.50, 0, "This is the orignal Coca Cola!", false, null, null);
        Product product2 = new Product(UUID.randomUUID(), "Cola", "imgPath", 1.50, 0, "This is the orignal Coca Cola!", false, null, null);

        productRepository.save(product1);
        productRepository.save(product2);

        //Act
//        Iterable<Product> products = productService.getAllProducts();
        Iterable<Product> products = productRepository.findAll();

        //Assert
        assertThat(products).hasSize(2);
    }
}
