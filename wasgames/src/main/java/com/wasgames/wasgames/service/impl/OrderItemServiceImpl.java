package com.wasgames.wasgames.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.wasgames.wasgames.model.OrderItem;
import com.wasgames.wasgames.repository.OrderItemRepository;
import com.wasgames.wasgames.service.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItem save(OrderItem item) {
        return orderItemRepository.save(item);
    }
}
