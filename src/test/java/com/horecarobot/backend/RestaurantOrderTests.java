package com.horecarobot.backend;

import TestEntities.RestaurantOrderTest;
import com.horecarobot.backend.Order.OrderService;
import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantOrderRepository;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RestaurantOrderTests {
    private final OrderService orderService;
    private final RestaurantOrderRepository restaurantOrderRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RestaurantOrderTests(OrderService orderService, RestaurantOrderRepository restaurantOrderRepository, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.restaurantOrderRepository = restaurantOrderRepository;
        this.modelMapper = modelMapper;
    }

    @Test
    public void Should_Add_Order() {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantOrderTest restaurantOrderTest = new RestaurantOrderTest(null, 10.50, false, null, restaurantTable, null);
        RestaurantOrderTest restaurantOrderTest2 = new RestaurantOrderTest(null, 10.50, false, null, restaurantTable, null);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrderTest.setProductOrders(productOrderList);
        restaurantOrderTest2.setProductOrders(productOrderList);

        //Act
        orderService.addOrder(convertToEntity(restaurantOrderTest2));

        restaurantOrderTest.setId(restaurantOrderRepository.findAll().get(0).getId());
        restaurantOrderTest.setCreatedAt(restaurantOrderRepository.findAll().get(0).getCreatedAt());

        //Assert
        assertThat(restaurantOrderRepository.findAll().get(0)).usingRecursiveComparison().isEqualTo(restaurantOrderTest);
    }

//    @Test
//    public void Should_Get_All_Restaurant_Orders() {
//        //Arrange
//        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
//        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);
//        RestaurantOrder restaurantOrder2 = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);
//
//        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
//        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);
//
//        List<ProductOrder> productOrderList = new ArrayList<>();
//        productOrderList.add(productOrder);
//
//        restaurantOrder.setProductOrders(productOrderList);
//        restaurantOrder2.setProductOrders(productOrderList);
//
//        restaurantOrderRepository.save(restaurantOrder);
//        restaurantOrderRepository.save(restaurantOrder2);
//
//        //Act
//        List<RestaurantOrder> restaurantOrdersList = orderService.getOrders();
//
//        //Assert
//        assertThat(restaurantOrdersList).hasSize(2);
//    }
//
//    @Test
//    public void Should_Get_Chosen_Restaurant_Orders() throws NotFoundException {
//        //Arrange
//        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
//        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);
//        RestaurantOrder restaurantOrder2 = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);
//
//        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
//        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);
//
//        List<ProductOrder> productOrderList = new ArrayList<>();
//        productOrderList.add(productOrder);
//
//        restaurantOrder.setProductOrders(productOrderList);
//        restaurantOrder2.setProductOrders(productOrderList);
//
//        restaurantOrderRepository.save(restaurantOrder);
//        restaurantOrderRepository.save(restaurantOrder2);
//
//        //Act
//        RestaurantOrder orderToCheck = orderService.getOrder(restaurantOrder.getId());
//
//        //Assert
//        assertThat(orderToCheck).usingRecursiveComparison().isEqualTo(restaurantOrder);
//    }

//    @Test
//    public void Should_Delete_Chosen_Restaurant_Order() throws NotFoundException {
//        //Arrange
//        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
//        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);
//        RestaurantOrder restaurantOrder2 = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);
//
//        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
//        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);
//
//        List<ProductOrder> productOrderList = new ArrayList<>();
//        productOrderList.add(productOrder);
//
//        restaurantOrder.setProductOrders(productOrderList);
//        restaurantOrder2.setProductOrders(productOrderList);
//
//        restaurantOrderRepository.save(restaurantOrder);
//        restaurantOrderRepository.save(restaurantOrder2);
//
//        restaurantOrder.setId(restaurantOrderRepository.findAll().get(0).getId());
//
//        List<RestaurantOrder> checkRestaurantOrderList = restaurantOrderRepository.findAll();
//        //Act
//        orderService.deleteOrder(restaurantOrder.getId());
//
//        //Assert
//        assertThat(checkRestaurantOrderList).hasSize(1);
//    }

    @Test()
    public void Should_Give_Not_Found_Exception_If_Restaurant_Order_Doesnt_Exist() {
        //Arrange
        UUID randomUUID = UUID.randomUUID();
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrder.setProductOrders(productOrderList);

        //Act
        Exception exception = assertThrows(NotFoundException.class, () -> orderService.getOrder(randomUUID));

        //Assert
        assertEquals(NotFoundException.class, exception.getClass());
    }

    public RestaurantOrder convertToEntity(RestaurantOrderTest restaurantOrderTest) {
        return modelMapper.map(restaurantOrderTest, RestaurantOrder.class);
    }
}
