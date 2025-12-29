package com.wasgames.wasgames.service;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wasgames.wasgames.dto.ProductDTO;
import com.wasgames.wasgames.model.Product;
import com.wasgames.wasgames.repository.CategoryRepository;
import com.wasgames.wasgames.repository.ProductRepository;
import com.wasgames.wasgames.service.impl.ProductServiceImpl;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void createProduct_success() {
        ProductDTO dto = new ProductDTO();
        dto.setName("Chess");
        dto.setPrice(BigDecimal.valueOf(50));
        dto.setStock(5);

        Product saved = new Product();
        saved.setId(1L);
        saved.setName("Chess");

        when(productRepository.save(any(Product.class)))
                .thenReturn(saved);

        ProductDTO result = productService.createProduct(dto);

        assertEquals(1L, result.getId());
        assertEquals("Chess", result.getName());
    }

    @Test
    void updateStock_success() {
        Product product = new Product();
        product.setId(1L);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductDTO dto = productService.updateStock(1L, 99);

        assertEquals(99, dto.getStock());
    }
}