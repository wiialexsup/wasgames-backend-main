package com.wasgames.wasgames.service;

import java.util.List;
import java.util.Optional;
import com.wasgames.wasgames.dto.ProductDTO;
import com.wasgames.wasgames.model.Product;

public interface ProductService {
    Product save(Product product);
    Optional<Product> findById(Long id);
    List<Product> findAll();
    void deleteById(Long id);

    List<Product> findByCategory(Long categoryId);
    List<Product> searchByName(String name);

    ProductDTO createProduct(ProductDTO productDTO);
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
    ProductDTO updateStock(Long id, Integer stock);
    ProductDTO updatePrice(Long id, String price);

    List<ProductDTO> getLowStockProducts(Integer threshold);
    List<ProductDTO> getAllProducts();
}