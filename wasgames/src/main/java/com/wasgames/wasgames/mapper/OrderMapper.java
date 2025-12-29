package com.wasgames.wasgames.mapper;

import java.util.stream.Collectors;

import com.wasgames.wasgames.dto.*;
import com.wasgames.wasgames.model.*;

public class OrderMapper {

    public static OrderDTO toDto(Order order) {
        OrderDTO dto = new OrderDTO();

        dto.setId(order.getId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setStatus(order.getStatus());
        dto.setTotalPrice(order.getTotalPrice());

        if (order.getItems() != null) {
            dto.setItems(
                order.getItems().stream()
                    .map(item -> {
                        OrderItemDTO itemDTO = new OrderItemDTO();
                        itemDTO.setId(item.getId());
                        itemDTO.setProductId(item.getProduct().getId());
                        itemDTO.setProductName(item.getProduct().getName());
                        itemDTO.setQuantity(item.getQuantity());
                        itemDTO.setPrice(item.getPrice());
                        return itemDTO;
                    })
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
