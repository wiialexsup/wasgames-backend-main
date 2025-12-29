package com.wasgames.wasgames.service;

import java.util.List;
import java.util.Optional;
import com.wasgames.wasgames.dto.CategoryDTO;
import com.wasgames.wasgames.model.Category;

public interface CategoryService {
    Category save(Category category);
    Optional<Category> findById(Long id);
    List<Category> findAll();
    void deleteById(Long id);

    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);
    void deleteCategory(Long id);
    List<CategoryDTO> getAllCategories();
    CategoryDTO getCategoryById(Long id);
}