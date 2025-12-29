package com.wasgames.wasgames.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.*;
import java.util.List;

import com.wasgames.wasgames.model.OrderStatus;

@Getter
@Setter
public class OrderDTO {

    private Long id;
    private LocalDateTime createdAt;
    private OrderStatus status;

    private BigDecimal totalPrice;

    private List<OrderItemDTO> items;
}
