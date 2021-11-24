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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    @Disabled
    public void Should_Add_Restaurant_Order() {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);
        RestaurantOrder restaurantOrder2 = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrder.setProductOrders(productOrderList);
        restaurantOrder2.setProductOrders(productOrderList);

        when(restaurantOrderRepository.save(restaurantOrder)).thenReturn(restaurantOrder);

        //Act
        orderService.addOrder(restaurantOrder2);

        //Arrange
        assertThat(restaurantOrder2).usingRecursiveComparison().isEqualTo(restaurantOrder);
        verify(restaurantOrderRepository, times(1)).save(restaurantOrder);
    }

    @Test
    public void Should_Get_All_Restaurant_Orders() {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        List<RestaurantOrder> restaurantOrderList = new ArrayList<>();
        restaurantOrderList.add(new RestaurantOrder(null, 10.50, false, null, restaurantTable, null));
        restaurantOrderList.add(new RestaurantOrder(null, 10.50, false, null, restaurantTable, null));

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrderList.get(0).setProductOrders(productOrderList);
        restaurantOrderList.get(1).setProductOrders(productOrderList);

        when(restaurantOrderRepository.findAll()).thenReturn(restaurantOrderList);

        //Act
        List<RestaurantOrder> emptyRestaurantOrderList = orderService.getOrders();

        //Assert
        assertEquals(2, emptyRestaurantOrderList.size());
        verify(restaurantOrderRepository, times(1)).findAll();
    }

    @Test
    public void Should_Get_Chosen_Restaurant_Orders() throws NotFoundException {
        //Arrange
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
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
        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
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
    @Disabled
    public void Should_Update_Chosen_Restaurant_Order() throws NotFoundException {
        RestaurantTable restaurantTable = new RestaurantTable(null, 1, 100, 40);
        RestaurantOrder restaurantOrder = new RestaurantOrder(null, 10.50, false, null, restaurantTable, null);

        Product product = new Product(null, "Coca cola", "imgPath", 2, 0, "Taste good", false, null, null, null);
        ProductOrder productOrder = new ProductOrder(null, OrderStatus.DELIVERED,  product, null);

        List<ProductOrder> productOrderList = new ArrayList<>();
        productOrderList.add(productOrder);

        restaurantOrder.setProductOrders(productOrderList);

        when(restaurantOrderRepository.findById(restaurantOrder.getId())).thenReturn(Optional.of(restaurantOrder));
        when(restaurantOrderRepository.save(restaurantOrder)).thenReturn(restaurantOrder);

        restaurantOrder.setSubTotal(11.50);
        //Act
        orderService.updateOrder(restaurantOrder);

        //Assert
        assertEquals(11.50, restaurantOrder.getSubTotal());
    }

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

        when(restaurantOrderRepository.findById(restaurantOrder.getId())).thenReturn(Optional.of(restaurantOrder));

        //Act
        Exception exception = assertThrows(NotFoundException.class, () -> orderService.getOrder(randomUUID));

        //Assert
        assertEquals(NotFoundException.class, exception.getClass());
    }
}
