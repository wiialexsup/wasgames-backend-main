package com.wasgames.wasgames.controller;

import com.wasgames.wasgames.dto.ProductDTO;
import com.wasgames.wasgames.dto.ImageDTO;
import com.wasgames.wasgames.dto.CategoryDTO;
import com.wasgames.wasgames.model.Product;
import com.wasgames.wasgames.model.Image;
import com.wasgames.wasgames.model.Category;
import com.wasgames.wasgames.service.ProductService;
import com.wasgames.wasgames.service.CategoryService;
import com.wasgames.wasgames.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO created = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductDTO productDTO) {
        ProductDTO updated = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/products/{id}/stock")
    public ResponseEntity<ProductDTO> updateStock(
            @PathVariable Long id,
            @RequestParam Integer stock) {
        ProductDTO updated = productService.updateStock(id, stock);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/products/{id}/price")
    public ResponseEntity<ProductDTO> updatePrice(
            @PathVariable Long id,
            @RequestParam String price) {
        ProductDTO updated = productService.updatePrice(id, price);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/products/{productId}/images")
    public ResponseEntity<ImageDTO> addImage(
            @PathVariable Long productId,
            @RequestBody ImageDTO imageDTO) {
        ImageDTO added = imageService.addImageToProduct(productId, imageDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/images/{imageId}")
    public ResponseEntity<ImageDTO> updateImage(
            @PathVariable Long imageId,
            @RequestBody ImageDTO imageDTO) {
        ImageDTO updated = imageService.updateImage(imageId, imageDTO);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/categories")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO created = categoryService.createCategory(categoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updated = categoryService.updateCategory(id, categoryDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/products/low-stock")
    public ResponseEntity<List<ProductDTO>> getLowStockProducts(@RequestParam(defaultValue = "10") Integer threshold) {
        List<ProductDTO> products = productService.getLowStockProducts(threshold);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/products/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}