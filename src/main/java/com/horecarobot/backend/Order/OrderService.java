package com.horecarobot.backend.Order;

import org.springframework.beans.factory.annotation.Autowired;
import edu.fontys.horecarobot.databaselibrary.models.Order;
import org.springframework.stereotype.Service;

import java.sql.ClientInfoStatus;
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

    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrder(UUID orderUUID) {
        Optional<Order> findOrder = orderRepository.findById(orderUUID);

        if (findOrder.isEmpty()) {
            throw new IllegalStateException(
                    "Order with id: " + orderUUID + " does not exist"
            );
        }
        return orderRepository.findById(orderUUID);
    }

    public void addOrder(Order order) {
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
