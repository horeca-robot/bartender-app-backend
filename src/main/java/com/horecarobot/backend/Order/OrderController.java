package com.horecarobot.backend.Order;

import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/order")
@CrossOrigin(origins = "*")
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
    public void createOrder(@RequestBody RestaurantOrderDTO orderDTO) {
        orderService.addOrder(convertToEntity(orderDTO));
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
}