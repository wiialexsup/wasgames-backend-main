package com.wasgames.wasgames.dto;

import java.math.BigDecimal;
import java.util.List;
import lombok.*;

@Getter
@Setter
public class ProductDTO {

    private Long id;
    private String name;
    private String description;

    private BigDecimal price;
    private Integer stock;

    private CategoryDTO category;

    private List<ImageDTO> images;

    private Integer minPlayers;
    private Integer maxPlayers;
    private Integer ageLimit;
    private Integer playTimeMinutes;
}
