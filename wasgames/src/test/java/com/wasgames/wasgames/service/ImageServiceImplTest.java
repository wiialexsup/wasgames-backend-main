package com.wasgames.wasgames.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wasgames.wasgames.dto.ImageDTO;
import com.wasgames.wasgames.model.Image;
import com.wasgames.wasgames.model.Product;
import com.wasgames.wasgames.repository.ImageRepository;
import com.wasgames.wasgames.repository.ProductRepository;
import com.wasgames.wasgames.service.impl.ImageServiceImpl;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    void addImageToProduct_success() {
        Product product = new Product();
        product.setId(1L);

        ImageDTO dto = new ImageDTO();
        dto.setUrl("img.png");

        Image saved = new Image();
        saved.setId(10L);
        saved.setUrl("img.png");

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));
        when(imageRepository.save(any(Image.class)))
                .thenReturn(saved);

        ImageDTO result = imageService.addImageToProduct(1L, dto);

        assertEquals(10L, result.getId());
        assertEquals("img.png", result.getUrl());
    }

    @Test
    void addImageToProduct_productNotFound() {
        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> imageService.addImageToProduct(1L, new ImageDTO()));
    }
}