package com.horecarobot.backend.Order;

import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.ProductOrder;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantOrderRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {
    private final RestaurantOrderRepository restaurantOrderRepository;
    private final ProductRepository productRepository;

    @Autowired
    public OrderService(RestaurantOrderRepository orderRepository, ProductRepository productRepository) {
        this.restaurantOrderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public Page<RestaurantOrder> getOrders(int page, int size) {
//        Page<RestaurantOrder> orders =  restaurantOrderRepository.findAllByOrderByOrderDoneAscCreatedAtAsc(PageRequest.of(page, size));
        List<RestaurantOrder> orders = restaurantOrderRepository.findAll();

        orders.sort(new OrderCompareDate());
        orders.sort(new OrderCompare());

        Pageable paging = PageRequest.of(page, size);
        int start = Math.min((int)paging.getOffset(), orders.size());
        int end = Math.min((start + paging.getPageSize()), orders.size());

        return new PageImpl<>(orders.subList(start, end), paging, orders.size());
    }

    public RestaurantOrder getOrder(UUID orderUUID) throws NotFoundException {
        return this.restaurantOrderRepository.findById(orderUUID).orElseThrow(() -> new NotFoundException("Cannot find object"));
    }

    public void addOrder(RestaurantOrder order) {
        Date currentDate = new Date();
        order.setCreatedAt(currentDate);
        order.setSubTotal(calculateSubTotal(order));

        for(ProductOrder productOrder: order.getProductOrders()) {
            productOrder.setOrderStatus(OrderStatus.PREPARING);
        }

        restaurantOrderRepository.save(order);
    }

    public void updateOrder(RestaurantOrder order) throws NotFoundException {
        this.getOrder(order.getId());
        order.setSubTotal(calculateSubTotal(order));

        restaurantOrderRepository.save(order);
    }

    private double calculateSubTotal(RestaurantOrder order) {
        double tempSubTotal = 0;

        for(ProductOrder productOrder: order.getProductOrders()) {
            tempSubTotal += productRepository.getById(productOrder.getProduct().getId()).getPrice();
        }

        return tempSubTotal;
    }

    public void deleteOrder(UUID orderID) throws NotFoundException {
        restaurantOrderRepository.delete(this.getOrder(orderID));
    }
}
