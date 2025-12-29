package com.wasgames.wasgames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wasgames.wasgames.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
