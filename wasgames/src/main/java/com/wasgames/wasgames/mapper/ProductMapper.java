package com.wasgames.wasgames.mapper;

import java.util.stream.Collectors;

import com.wasgames.wasgames.dto.*;
import com.wasgames.wasgames.model.*;

public class ProductMapper {

    public static ProductDTO toDto(Product product) {
        ProductDTO dto = new ProductDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setMinPlayers(product.getMinPlayers());
        dto.setMaxPlayers(product.getMaxPlayers());
        dto.setAgeLimit(product.getAgeLimit());
        dto.setPlayTimeMinutes(product.getPlayTimeMinutes());

        if (product.getCategory() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(product.getCategory().getId());
            categoryDTO.setName(product.getCategory().getName());
            dto.setCategory(categoryDTO);
        }

        if (product.getImages() != null) {
            dto.setImages(
                product.getImages().stream()
                    .map(img -> {
                        ImageDTO imageDTO = new ImageDTO();
                        imageDTO.setId(img.getId());
                        imageDTO.setUrl(img.getUrl());
                        return imageDTO;
                    })
                    .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
