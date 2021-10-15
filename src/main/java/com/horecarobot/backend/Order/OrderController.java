package com.horecarobot.backend.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.annotation.*;

import edu.fontys.horecarobot.databaselibrary.enums.PaymentStatus;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
    public Optional<RestaurantOrder> getOrder(@PathVariable("orderUUID") UUID orderUUID) {
        Optional<RestaurantOrder> retrievedOrder = orderService.getOrder(orderUUID);

        if(retrievedOrder.isEmpty()) {
            return null;
        }
        
        return retrievedOrder;
    }

    @GetMapping(path = "/statusses/payment")
    public List<PaymentStatus> getPaymentStatusses() {
        return Arrays.asList(PaymentStatus.values());
    }

    @GetMapping(path = "/statusses/delivery")
    public List<PaymentStatus> getDeliveryStatusses() {
        return Arrays.asList(PaymentStatus.values());
    }

    @PostMapping
    public void createOrder(@RequestBody RestaurantOrder order) {
        orderService.addOrder(order);
    }

    @PutMapping(path = "/{orderUUID}")
    public void updateOrder(@PathVariable("orderUUID") UUID orderUUID, @RequestBody RestaurantOrder order) throws MissingRequestValueException, Exception {
        if(order.getId() == null) {
            throw new MissingRequestValueException("Field missing on order object: \"id\"");
        }
        
        if (this.orderIdMatchesWithGivenId(orderUUID, order.getId())) {
            throw new Exception("Field \"id\" on order object does not match the path ID");
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

        if(strExpectedId != strGivenId) {
            return false;
        }

        return true;
    }
}
