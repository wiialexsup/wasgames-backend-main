package com.wasgames.wasgames.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.wasgames.wasgames.dto.CreateOrderRequest;
import com.wasgames.wasgames.dto.OrderDTO;
import com.wasgames.wasgames.mapper.OrderMapper;
import com.wasgames.wasgames.model.Order;
import com.wasgames.wasgames.service.OrderService;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // POST /api/orders/create?userId=1
    @PostMapping("/create")
    public OrderDTO createOrder(@RequestParam Long userId) {
        Order order = orderService.createOrder(userId);
        return OrderMapper.toDto(order);
    }

    // GET /api/orders/{id}
    @GetMapping("/{id}")
    public OrderDTO getById(@PathVariable Long id) {
        return orderService.findById(id)
                .map(OrderMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    // GET /api/orders/user/{userId}
    @GetMapping("/user/{userId}")
    public List<OrderDTO> getByUser(@PathVariable Long userId) {
        return orderService.findByUser(userId)
                .stream()
                .map(OrderMapper::toDto)
                .collect(Collectors.toList());
    }
}
