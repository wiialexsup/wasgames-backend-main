package com.wasgames.wasgames.service;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.wasgames.wasgames.dto.CategoryDTO;
import com.wasgames.wasgames.model.Category;
import com.wasgames.wasgames.repository.CategoryRepository;
import com.wasgames.wasgames.service.impl.CategoryServiceImpl;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void createCategory_success() {
        CategoryDTO dto = new CategoryDTO();
        dto.setName("Board Games");

        Category saved = new Category();
        saved.setId(1L);
        saved.setName("Board Games");

        when(categoryRepository.save(any(Category.class)))
                .thenReturn(saved);

        CategoryDTO result = categoryService.createCategory(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Board Games", result.getName());
    }

    @Test
    void getCategoryById_notFound() {
        when(categoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> categoryService.getCategoryById(1L));
    }
}
