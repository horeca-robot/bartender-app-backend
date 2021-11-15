package com.horecarobot.backend.Order;

import com.horecarobot.backend.Product.CreateOrderProductDTO;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/order")
@CrossOrigin(origins = "http://localhost:8081")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<RestaurantOrderDTO> getOrders() {
        List<RestaurantOrder> orders = orderService.getOrders();
        return orders.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @GetMapping(path = "/{orderUUID}")
    public RestaurantOrderDTO getOrder(@PathVariable("orderUUID") UUID orderUUID) throws NotFoundException {
        return convertToDTO(this.orderService.getOrder(orderUUID));
    }

    @GetMapping(path = "/status/delivery")
    public List<OrderStatus> getDeliveryStatus() {
        return Arrays.asList(OrderStatus.values());
    }

    @PostMapping
    public void createOrder(@RequestBody CreateRestaurantOrderDTO createOrderDTO) {
        orderService.addOrder(convertCreateToEntity(createOrderDTO));
    }

    @PutMapping(path = "/{orderUUID}")
    public void updateOrder(@PathVariable("orderUUID") UUID orderUUID, @RequestBody RestaurantOrderDTO orderDTO) throws NotFoundException {
        orderDTO.setId(orderUUID);
        orderService.updateOrder(convertToEntity(orderDTO));
    }

    @DeleteMapping(path = "{orderID}")
    public void deleteOrder(@PathVariable("orderID") UUID orderID) throws NotFoundException {
        orderService.deleteOrder(orderID);
    }

    // Mappers
    private RestaurantOrder convertToEntity(RestaurantOrderDTO restaurantOrderDTO) {
        return modelMapper.map(restaurantOrderDTO, RestaurantOrder.class);
    }

    private RestaurantOrderDTO convertToDTO(RestaurantOrder restaurantOrder) {
        return modelMapper.map(restaurantOrder, RestaurantOrderDTO.class);
    }

    private RestaurantOrder convertCreateToEntity(CreateRestaurantOrderDTO createRestaurantOrderDTO) {
        RestaurantOrder order = new RestaurantOrder();
        List<ProductOrder> orderProducts = new ArrayList<>();

        order.setTable(createRestaurantOrderDTO.getTable());

        for (CreateOrderProductDTO product : createRestaurantOrderDTO.getProducts()) {
            for (int i = 0; i < product.getCount(); i++) {
                ProductOrder newProductOrder = new ProductOrder();

                newProductOrder.setProduct(modelMapper.map(product, Product.class));
                orderProducts.add(newProductOrder);
            }
        }
        order.setProductOrders(orderProducts);

        return order;
    }
}