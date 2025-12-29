package com.wasgames.wasgames.service;

import java.util.List;
import java.util.Optional;

import com.wasgames.wasgames.model.Order;
import com.wasgames.wasgames.model.OrderStatus;

public interface OrderService {

    Order createOrder(Long userId);

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findByUser(Long userId);

    List<Order> findByStatus(OrderStatus status);

    void deleteById(Long id);
}
