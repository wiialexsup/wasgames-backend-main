package com.wasgames.wasgames.repository;

import com.wasgames.wasgames.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByStockLessThan(Integer threshold);
}