package com.horecarobot.backend.Order;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import edu.fontys.horecarobot.databaselibrary.models.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/order")
@CrossOrigin(origins = "http://localhost:8081")
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> Orders() {
        return orderService.getOrders();
    }

    @GetMapping(path = "/{orderUUID}")
    public Optional<Order> getBankAccount(@PathVariable("orderUUID") String orderUUID) {
        return orderService.getOrder(orderUUID);
    }

    @PostMapping
    public void createOrder(@RequestBody Order order) {
        orderService.addOrder(order);
    }
}
