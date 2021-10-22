package com.horecarobot.backend;

import com.horecarobot.backend.Product.ProductService;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
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
        Product product = new Product(null, "Cola", "imgPath", 1.50, 0, "This is the original Coca Cola!", false, null, null);
        Product product2 = new Product(null, "Cola", "imgPath", 1.50, 0, "This is the original Coca Cola!", false, null, null);

        //Act
        productService.addProduct(product2);
        product.setId(product2.getId());

        assertThat(productRepository.findAll().get(0)).usingRecursiveComparison().isEqualTo(product);
    }

    @Test
    public void Should_Get_All_Products() {
        //Arrange
        Product product1 = new Product(UUID.randomUUID(), "Cola", "imgPath", 1.50, 0, "This is the orignal Coca Cola!", false, null, null);
        Product product2 = new Product(UUID.randomUUID(), "Ice Tea", "imgPath", 1.50, 0, "Nice peach ice tea!", false, null, null);

        productRepository.save(product1);
        productRepository.save(product2);

        //Act
        Iterable<Product> products = productService.getAllProducts();

        //Assert
        assertThat(products).hasSize(2);
    }
}
