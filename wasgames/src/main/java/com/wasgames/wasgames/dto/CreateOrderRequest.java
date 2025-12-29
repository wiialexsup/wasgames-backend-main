package com.wasgames.wasgames.dto;

import java.util.List;
import lombok.*;

@Getter
@Setter
public class CreateOrderRequest {

    private List<OrderItemRequest> items;
}
