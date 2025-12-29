package com.wasgames.wasgames.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wasgames.wasgames.model.OrderItem;
import com.wasgames.wasgames.repository.OrderItemRepository;
import com.wasgames.wasgames.service.impl.OrderItemServiceImpl;

@ExtendWith(MockitoExtension.class)
class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    @Test
    void saveOrderItem_success() {
        OrderItem item = new OrderItem();
        item.setQuantity(3);

        when(orderItemRepository.save(item))
                .thenReturn(item);

        OrderItem saved = orderItemService.save(item);

        assertEquals(3, saved.getQuantity());
    }
}