package com.wasgames.wasgames.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wasgames.wasgames.model.Order;
import com.wasgames.wasgames.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);
}
