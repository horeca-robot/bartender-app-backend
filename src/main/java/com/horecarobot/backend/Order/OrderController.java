package com.horecarobot.backend.Order;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/order")
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<RestaurantOrder> getOrders() {
        return orderService.getOrders();
    }

    @GetMapping(path = "/{orderUUID}")
    public RestaurantOrder getOrder(@PathVariable("orderUUID") UUID orderUUID) throws NotFoundException {
        return this.orderService.getOrder(orderUUID);
    }

    @GetMapping(path = "/status/delivery")
    public List<OrderStatus> getDeliveryStatus() {
        return Arrays.asList(OrderStatus.values());
    }

    @PostMapping
    public void createOrder(@RequestBody RestaurantOrder order) {
        orderService.addOrder(order);
    }

    @PutMapping(path = "/{orderUUID}")
    public void updateOrder(@PathVariable("orderUUID") UUID orderUUID, @RequestBody RestaurantOrder order) throws NotFoundException {
        order.setId(orderUUID);
        orderService.updateOrder(order);
    }

    @DeleteMapping(path = "{orderID}")
    public void deleteOrder(@PathVariable("orderID") UUID orderID) throws NotFoundException {
        orderService.deleteOrder(orderID);
    }
}
