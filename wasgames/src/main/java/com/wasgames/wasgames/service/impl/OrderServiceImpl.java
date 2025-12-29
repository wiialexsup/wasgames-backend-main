package com.wasgames.wasgames.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.wasgames.wasgames.model.Order;
import com.wasgames.wasgames.model.OrderStatus;
import com.wasgames.wasgames.model.User;
import com.wasgames.wasgames.repository.OrderRepository;
import com.wasgames.wasgames.repository.UserRepository;
import com.wasgames.wasgames.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order createOrder(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.NEW);
        order.setCreatedAt(LocalDateTime.now());

        return orderRepository.save(order);
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}
