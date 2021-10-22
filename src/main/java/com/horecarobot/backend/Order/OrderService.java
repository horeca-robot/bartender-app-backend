package com.horecarobot.backend.Order;

import edu.fontys.horecarobot.databaselibrary.repositories.OrderRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import org.springframework.stereotype.Service;

import java.util.*;

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

    public RestaurantOrder getOrder(UUID orderUUID) throws NotFoundException {
        return this.orderRepository.findById(orderUUID).orElseThrow(() -> new NotFoundException("Cannot find object"));
    }

    public void addOrder(RestaurantOrder order) {
        Date currentDate = new Date();
        order.setCreatedAt(currentDate);
        
        orderRepository.save(order);
    }

    public void updateOrder(RestaurantOrder order) throws NotFoundException {
        this.getOrder(order.getId());
        orderRepository.save(order);
    }

    public void deleteOrder(UUID orderID) throws NotFoundException {
        orderRepository.delete(this.getOrder(orderID));
    }

}
