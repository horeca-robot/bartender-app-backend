package com.horecarobot.backend.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.horecarobot.backend.Views.BasicView;

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
    public Optional<RestaurantOrder> getOrder(@PathVariable("orderUUID") UUID orderUUID) {
        Optional<RestaurantOrder> retrievedOrder = orderService.getOrder(orderUUID);

        if(retrievedOrder.isEmpty()) {
            return null;
        }
        
        return retrievedOrder;
    }

    @GetMapping(path = "/statusses/delivery")
    public List<OrderStatus> getDeliveryStatusses() {
        return Arrays.asList(OrderStatus.values());
    }

    @PostMapping
    public BasicView createOrder(@RequestBody RestaurantOrder order) {
        BasicView responseView = new BasicView();

        orderService.addOrder(order);
        responseView.setMessage("Order successfully added.");

        return responseView;
    }

    @PutMapping(path = "/{orderUUID}")
    public BasicView updateOrder(@PathVariable("orderUUID") UUID orderUUID, @RequestBody RestaurantOrder order) throws ResponseStatusException {
        BasicView responseView = new BasicView();
        
        if(order.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field missing on order object: \"id\"");
        }
        
        if (this.orderIdMatchesWithGivenId(orderUUID, order.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Field \"id\" on order object does not match the path ID");
        }

        if(!this.orderWithIDExists(orderUUID)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Order with \"id\":" + orderUUID.toString() + " does not exist.");
        }

        orderService.updateOrder(order);
        responseView.setMessage("Order successfully updated.");

        return responseView;
    }

    @DeleteMapping(path = "{orderID}")
    public void deleteOrder(@PathVariable("orderID") UUID orderID) {
        orderService.deleteOrder(orderID);
    }

    private boolean orderIdMatchesWithGivenId(UUID expectedId, UUID givenId) {
        String strExpectedId = expectedId.toString();
        String strGivenId = givenId.toString();

        if(strExpectedId != strGivenId) {
            return false;
        }

        return true;
    }

    private boolean orderWithIDExists(UUID orderId) {
        Optional<RestaurantOrder> order = this.orderService.getOrder(orderId);

        if(order.isEmpty()) {
            return false;
        }

        return true;
    }
}
