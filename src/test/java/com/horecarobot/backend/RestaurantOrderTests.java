package com.horecarobot.backend;

import TestEntities.RestaurantOrderTest;
import com.horecarobot.backend.Order.OrderService;
import com.horecarobot.backend.Order.RestaurantOrderDTO;
import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantTable;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantOrderRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
    @Disabled
    public void Should_Add_Order() {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantOrderTest restaurantOrderTest = new RestaurantOrderTest(null, 10.50, false, null, restaurantTable, null);
        RestaurantOrderTest restaurantOrderTest2 = new RestaurantOrderTest(null, 10.50, false, null, restaurantTable, null);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED, convertToEntity(restaurantOrderTest), product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrderTest.setProductOrders(productOrderList);
        restaurantOrderTest2.setProductOrders(productOrderList);

        //Act
        orderService.addOrder(convertToEntity(restaurantOrderTest2));
        restaurantOrderTest.setId(restaurantOrderTest2.getId());

        //List<RestaurantOrder> kek = restaurantOrderRepository.findAll();

        //Assert
        assertThat(restaurantOrderRepository.findAll().get(0)).usingRecursiveComparison().isEqualTo(restaurantOrderTest);
    }

    private RestaurantOrder convertToEntity(RestaurantOrderTest restaurantOrderTest) {
        return modelMapper.map(restaurantOrderTest, RestaurantOrder.class);
    }
}
