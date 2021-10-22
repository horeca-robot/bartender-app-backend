package com.horecarobot.backend.Order;

import edu.fontys.horecarobot.databaselibrary.repositories.OrderRepository;
import javassist.NotFoundException;
import edu.fontys.horecarobot.databaselibrary.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<RestaurantOrder> getOrders() {
        return orderRepository.findAll();
    }

    public RestaurantOrder getOrder(UUID orderUUID) {
        Optional<RestaurantOrder> order = this.orderRepository.findById(orderUUID);
        return order.orElse(null);
    }

    public void addOrder(RestaurantOrder order) {
        Date currentDate = new Date();
        order.setCreatedAt(currentDate);
        
        orderRepository.save(order);
    }

    public void updateOrder(RestaurantOrder order) {
        UUID orderId = order.getId();
        Optional<RestaurantOrder> existingOrder = orderRepository.findById(orderId);

        if(existingOrder.isEmpty()) {
            throw new IllegalStateException(
                "Order with ID: " + orderId.toString() + " does not exist."
            );
        }

        orderRepository.save(order);
    }

    public void deleteOrder(UUID orderID) {
        orderRepository.delete(this.orderExists(orderID));
    }
    public RestaurantOrder orderExists(UUID orderID) {
        Optional<RestaurantOrder> order = this.orderRepository.findById(orderID);
        if(!order.isPresent()){
            return null;
        }
        return order.get();
    }

}
