package com.wasgames.wasgames.dto;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
public class OrderItemDTO {
    private Long id;
    private Long productId;
    private String productName;

    private Integer quantity;
    private BigDecimal price;
}
