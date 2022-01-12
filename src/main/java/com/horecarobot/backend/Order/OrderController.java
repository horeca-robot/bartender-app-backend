package com.horecarobot.backend.Order;

import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.fontys.horecarobot.databaselibrary.enums.OrderStatus;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantOrder;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/v1/order")
@CrossOrigin(origins = {"http://localhost:8081", "https://rigs.bryanaafjes.nl", "https://rigs.bryanaafjes.nl:444"})
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(OrderService orderService, ModelMapper modelMapper) {
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOrders(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "2") int size
    ) {

        Page<RestaurantOrder> orders = orderService.getOrders(page, size);
        List<RestaurantOrderDTO> orderDTOS = orders.stream().map(this::convertToDTO).collect(Collectors.toList());
        if (orderDTOS.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("current", orders.getNumber());
        response.put("total", orders.getTotalPages());
        response.put("totalItems", orders.getTotalElements());
        response.put("orders", orderDTOS);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(path = "/{orderUUID}")
    public RestaurantOrderDTO getOrder(@PathVariable("orderUUID") UUID orderUUID) throws NotFoundException {
        return convertToDTO(this.orderService.getOrder(orderUUID));
    }

    @GetMapping(path = "/status/delivery")
    public List<OrderStatus> getDeliveryStatus() {
        return Arrays.asList(OrderStatus.values());
    }

    @PostMapping
    public void createOrder(@RequestBody RestaurantOrderDTO createOrderDTO) {
        orderService.addOrder(convertToEntity(createOrderDTO));
    }

    @PutMapping(path = "/{orderUUID}")
    public void updateOrder(@PathVariable("orderUUID") UUID orderUUID, @RequestBody RestaurantOrderDTO updateOrderDTO) throws NotFoundException {
        RestaurantOrder order = convertToEntity(updateOrderDTO);
        order.setId(orderUUID);
        orderService.updateOrder(order);
    }

    @DeleteMapping(path = "{orderID}")
    public void deleteOrder(@PathVariable("orderID") UUID orderID) throws NotFoundException {
        orderService.deleteOrder(orderID);
    }

    // Mappers
    private RestaurantOrder convertToEntity(RestaurantOrderDTO restaurantOrderDTO) {
        return modelMapper.map(restaurantOrderDTO, RestaurantOrder.class);
    }

    private RestaurantOrderDTO convertToDTO(RestaurantOrder restaurantOrder) {
        return modelMapper.map(restaurantOrder, RestaurantOrderDTO.class);
    }
}