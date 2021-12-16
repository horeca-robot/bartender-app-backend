package com.horecarobot.backend;

import com.horecarobot.backend.Order.OrderService;
import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantOrderRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

@SpringBootTest
public class RestaurantOrderTests {
    @InjectMocks
    private OrderService orderService;

    @Mock
    private RestaurantOrderRepository restaurantOrderRepository;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        orderService = new OrderService(restaurantOrderRepository, productRepository);
        openMocks(orderService);
    }

    @Test
    public void Should_Add_Restaurant_Order() {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);

        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 2, false, null, "note", restaurantTable, null, true);
        RestaurantOrder restaurantOrder2 = new RestaurantOrder(null, 2, false, null, "note", restaurantTable, null, true);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, false, null, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrder.setProductOrders(productOrderList);
        restaurantOrder2.setProductOrders(productOrderList);

        when(productRepository.getById(product.getId())).thenReturn(product);

        //Act
        orderService.addOrder(restaurantOrder2);
        restaurantOrder.setCreatedAt(restaurantOrder2.getCreatedAt());

        //Arrange
        assertThat(restaurantOrder2).usingRecursiveComparison().isEqualTo(restaurantOrder);
        verify(restaurantOrderRepository, times(1)).save(restaurantOrder);
    }

    @Test
    public void Should_Get_All_Restaurant_Orders() {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);

        List<RestaurantOrder> restaurantOrderList = new ArrayList<>();

        restaurantOrderList.add(new RestaurantOrder(null, 10.50, false, null, "note", restaurantTable, null, true));
        restaurantOrderList.add(new RestaurantOrder(null, 10.50, false, null, "note", restaurantTable, null, true));

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, false, null, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrderList.get(0).setProductOrders(productOrderList);
        restaurantOrderList.get(1).setProductOrders(productOrderList);

        Page<RestaurantOrder> pageRestaurantOrderList = new PageImpl<>(restaurantOrderList);

        when(restaurantOrderRepository.findAll(PageRequest.of(0,5))).thenReturn(pageRestaurantOrderList);

        //Act
        Page<RestaurantOrder> pageWithOrders = orderService.getOrders(0,5);

        //Assert
        assertEquals(2, pageWithOrders.stream().count());
        verify(restaurantOrderRepository, times(1)).findAll(PageRequest.of(0,5));
    }

    @Test
    public void Should_Get_Chosen_Restaurant_Orders() throws NotFoundException {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);

        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, "note", restaurantTable, null, true);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, false, null, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrder.setProductOrders(productOrderList);

        when(restaurantOrderRepository.findById(restaurantOrder.getId())).thenReturn(Optional.of(restaurantOrder));

        //Act
        RestaurantOrder restaurantOrderToCheck = orderService.getOrder(restaurantOrder.getId());

        //Assert
        assertThat(restaurantOrderToCheck).usingRecursiveComparison().isEqualTo(restaurantOrder);
        verify(restaurantOrderRepository, times(1)).findById(restaurantOrder.getId());
    }

    @Test
    public void Should_Delete_Chosen_Restaurant_Order() throws NotFoundException {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);

        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, "note", restaurantTable, null, true);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, false, null, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrder.setProductOrders(productOrderList);

        when(restaurantOrderRepository.findById(restaurantOrder.getId())).thenReturn(Optional.of(restaurantOrder));

        //Act
        orderService.deleteOrder(restaurantOrder.getId());

        //Assert
        verify(restaurantOrderRepository, times(1)).delete(restaurantOrder);
        verify(restaurantOrderRepository, times(1)).findById(restaurantOrder.getId());
    }

    @Test
    public void Should_Update_Chosen_Restaurant_Order() throws NotFoundException {
        //Arrange
        Date currentDate = new Date();

        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);

        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 2, false, currentDate, "note", restaurantTable, null, true);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, false, null, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrder.setProductOrders(productOrderList);

        when(productRepository.getById(product.getId())).thenReturn(product);
        when(restaurantOrderRepository.findById(restaurantOrder.getId())).thenReturn(Optional.of(restaurantOrder));
        when(restaurantOrderRepository.save(restaurantOrder)).thenReturn(restaurantOrder);

        RestaurantTable restaurantTableUpdate = new RestaurantTable(null, 2, 100, 40);

        restaurantOrder.setTable(restaurantTableUpdate);
        //Act
        orderService.updateOrder(restaurantOrder);

        //Assert
        assertEquals(2, restaurantOrder.getTable().getTableNumber());
    }

    @Test()
    public void Should_Give_Not_Found_Exception_If_Restaurant_Order_Doesnt_Exist() {
        //Arrange
        UUID randomUUID = UUID.randomUUID();
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);

        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, "note", restaurantTable, null, true);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, false, null, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrder.setProductOrders(productOrderList);

        when(restaurantOrderRepository.findById(restaurantOrder.getId())).thenReturn(Optional.of(restaurantOrder));

        //Act
        Exception exception = assertThrows(NotFoundException.class, () -> orderService.getOrder(randomUUID));

        //Assert
        assertEquals(NotFoundException.class, exception.getClass());
    }
}
