package com.wasgames.wasgames.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.wasgames.wasgames.dto.ProductDTO;
import com.wasgames.wasgames.mapper.ProductMapper;
import com.wasgames.wasgames.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // GET /api/products
    @GetMapping
    public List<ProductDTO> getAll() {
        return productService.findAll()
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    // GET /api/products/{id}
    @GetMapping("/{id}")
    public ProductDTO getById(@PathVariable Long id) {
        return productService.findById(id)
                .map(ProductMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    // GET /api/products/search?name=uno
    @GetMapping("/search")
    public List<ProductDTO> search(@RequestParam String name) {
        return productService.searchByName(name)
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }

    // GET /api/products/category/{categoryId}
    @GetMapping("/category/{categoryId}")
    public List<ProductDTO> getByCategory(@PathVariable Long categoryId) {
        return productService.findByCategory(categoryId)
                .stream()
                .map(ProductMapper::toDto)
                .collect(Collectors.toList());
    }
}
