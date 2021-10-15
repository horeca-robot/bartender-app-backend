package com.horecarobot.backend.Order;

import org.springframework.beans.factory.annotation.Autowired;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantOrderRepository;

import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderService {
    private final RestaurantOrderRepository orderRepository;

    @Autowired
    public OrderService(RestaurantOrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<RestaurantOrder> getOrders() {
        return orderRepository.findAll();
    }

    public Optional<RestaurantOrder> getOrder(UUID orderUUID) {
        return orderRepository.findById(orderUUID);
    }

    public void addOrder(RestaurantOrder order) {
        Date currentDate = new Date();
        order.setCreated_at(currentDate);
        
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
        boolean findOrder = orderRepository.existsById(orderID);

        if  (!findOrder) {
            throw new IllegalStateException(
                    "Order with id: " + orderID + " does not exist"
            );
        }
        orderRepository.deleteById(orderID);
    }
}
