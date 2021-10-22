package com.horecarobot.backend.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
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
        List<RestaurantOrder> orders = orderService.getOrders();
        return orders;
    }

    @GetMapping(path = "/{orderUUID}")
    public RestaurantOrder getOrder(@PathVariable("orderUUID") UUID orderUUID) {

        RestaurantOrder order = this.orderService.getOrder(orderUUID);
        if(order == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with \"id\":" + orderUUID.toString() + " does not exist.");
        }
        return order;
    }

    @GetMapping(path = "/statusses/delivery")
    public List<OrderStatus> getDeliveryStatusses() {
        return Arrays.asList(OrderStatus.values());
    }

    @PostMapping
    public void createOrder(@RequestBody RestaurantOrder order) {
        orderService.addOrder(order);
    }

    @PutMapping(path = "/{orderUUID}")
    public void updateOrder(@PathVariable("orderUUID") UUID orderUUID, @RequestBody RestaurantOrder order) throws ResponseStatusException {

        if(order.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field missing on order object: \"id\"");
        }
        
        if (this.orderIdMatchesWithGivenId(orderUUID, order.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field \"id\" on order object does not match the path ID");
        }

        if(this.orderService.getOrder(orderUUID) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order with \"id\":" + orderUUID.toString() + " does not exist.");
        }

        orderService.updateOrder(order);
    }

    @DeleteMapping(path = "{orderID}")
    public void deleteOrder(@PathVariable("orderID") UUID orderID) {
        orderService.deleteOrder(orderID);
    }

    private boolean orderIdMatchesWithGivenId(UUID expectedId, UUID givenId) {
        String strExpectedId = expectedId.toString();
        String strGivenId = givenId.toString();
        return Objects.equals(strExpectedId, strGivenId);
    }
}
