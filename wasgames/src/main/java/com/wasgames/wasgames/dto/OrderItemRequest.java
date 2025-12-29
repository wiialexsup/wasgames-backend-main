package com.wasgames.wasgames.dto;

import lombok.*;

@Getter
@Setter
public class OrderItemRequest {

    private Long productId;
    private Integer quantity;
}
