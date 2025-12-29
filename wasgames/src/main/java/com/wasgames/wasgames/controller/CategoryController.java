package com.wasgames.wasgames.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.wasgames.wasgames.dto.CategoryDTO;
import com.wasgames.wasgames.model.Category;
import com.wasgames.wasgames.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.findAll();
    }

    @PostMapping
    public Category create(@RequestBody Category category) {
        return categoryService.save(category);
    }
}
