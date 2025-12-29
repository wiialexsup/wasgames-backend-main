package com.wasgames.wasgames.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.wasgames.wasgames.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
