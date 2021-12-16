package com.horecarobot.backend;

import com.horecarobot.backend.Product.ProductService;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ProductTests {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        productService = new ProductService(productRepository);
        MockitoAnnotations.openMocks(productService);
    }

    @Test
    public void Should_Add_Product() {
        //Arrange
        Product product = new Product(null, "Cola", "imgPath", 1.50, 0, "This is the original Coca Cola!", false, false, null, null, null, null);
        Product product2 = new Product(null, "Cola", "imgPath", 1.50, 0, "This is the original Coca Cola!", false, false, null, null, null, null);

        //Act
        productService.saveProduct(product2);

        //Arrange
        assertThat(product2).usingRecursiveComparison().isEqualTo(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void Should_Get_All_Products() {
        //Arrange
        List<Product> productList = new ArrayList<>();
        productList.add(new Product(UUID.randomUUID(), "Cola", "imgPath", 1.50, 0, "This is the orignal Coca Cola!", false, false, null, null, null, null));
        productList.add(new Product(UUID.randomUUID(), "Ice Tea", "imgPath", 1.50, 0, "Nice peach ice tea!", false, false, null, null, null, null));

        when(productRepository.findAll()).thenReturn(productList);

        //Act
        List<Product> listWithProducts = productService.getAllProducts();

        //Assert
        assertEquals(2, listWithProducts.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void Should_Get_Chosen_Product() throws NotFoundException {
        //Arrange
        Product product = new Product(null, "Cola", "imgPath", 1.50, 0, "This is the orignal Coca Cola!", false, false, null, null, null, null);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        //Act
        Product productToCheck = productService.getProduct(product.getId());

        //Assert
        assertThat(productToCheck).usingRecursiveComparison().isEqualTo(product);
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test
    public void Should_Delete_Chosen_Product() throws NotFoundException {
        //Arrange
        Product product = new Product(null, "Cola", "imgPath", 1.50, 0, "This is the orignal Coca Cola!", false,false, null, null, null, null);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        //Act
        productService.deleteProduct(product.getId());

        //Assert
        verify(productRepository, times(1)).delete(product);
        verify(productRepository, times(1)).findById(product.getId());
    }

    @Test()
    public void Should_Give_Not_Found_Exception_If_Product_Doesnt_Exist() {
        //Arrange
        UUID randomUUID = UUID.randomUUID();
        Product product = new Product(null, "Cola", "imgPath", 1.50, 0, "This is the orignal Coca Cola!", false, false, null, null, null, null);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        //Act
        Exception exception = assertThrows(NotFoundException.class, () -> productService.getProduct(randomUUID));
        //Assert
        assertEquals(NotFoundException.class, exception.getClass());
    }
}
