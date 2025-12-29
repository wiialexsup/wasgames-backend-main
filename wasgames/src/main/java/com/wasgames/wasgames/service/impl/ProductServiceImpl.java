package com.wasgames.wasgames.service.impl;

import com.wasgames.wasgames.dto.ProductDTO;
import com.wasgames.wasgames.dto.ImageDTO;
import com.wasgames.wasgames.dto.CategoryDTO;
import com.wasgames.wasgames.model.Product;
import com.wasgames.wasgames.model.Category;
import com.wasgames.wasgames.repository.ProductRepository;
import com.wasgames.wasgames.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wasgames.wasgames.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;


    @Override
    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = new Product();
        mapDtoToEntity(productDTO, product);
        Product saved = productRepository.save(product);
        return mapEntityToDto(saved);
    }

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        mapDtoToEntity(productDTO, product);
        Product updated = productRepository.save(product);
        return mapEntityToDto(updated);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ProductDTO updateStock(Long id, Integer stock) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setStock(stock);
        Product updated = productRepository.save(product);
        return mapEntityToDto(updated);
    }

    @Override
    @Transactional
    public ProductDTO updatePrice(Long id, String price) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        product.setPrice(new BigDecimal(price));
        Product updated = productRepository.save(product);
        return mapEntityToDto(updated);
    }

    @Override
    public List<ProductDTO> getLowStockProducts(Integer threshold) {
        return productRepository.findByStockLessThan(threshold)
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private void mapDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setStock(dto.getStock());
        entity.setMinPlayers(dto.getMinPlayers());
        entity.setMaxPlayers(dto.getMaxPlayers());
        entity.setAgeLimit(dto.getAgeLimit());
        entity.setPlayTimeMinutes(dto.getPlayTimeMinutes());

        if (dto.getCategory() != null && dto.getCategory().getId() != null) {
            Category category = categoryRepository.findById(dto.getCategory().getId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + dto.getCategory().getId()));
            entity.setCategory(category);
        }
    }

    private ProductDTO mapEntityToDto(Product entity) {
        ProductDTO dto = new ProductDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setPrice(entity.getPrice());
        dto.setStock(entity.getStock());
        dto.setMinPlayers(entity.getMinPlayers());
        dto.setMaxPlayers(entity.getMaxPlayers());
        dto.setAgeLimit(entity.getAgeLimit());
        dto.setPlayTimeMinutes(entity.getPlayTimeMinutes());

        if (entity.getCategory() != null) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setId(entity.getCategory().getId());
            categoryDTO.setName(entity.getCategory().getName());
            dto.setCategory(categoryDTO);
        }

        if (entity.getImages() != null && !entity.getImages().isEmpty()) {
            List<ImageDTO> imageDTOs = entity.getImages().stream()
                    .map(img -> {
                        ImageDTO imgDTO = new ImageDTO();
                        imgDTO.setId(img.getId());
                        imgDTO.setUrl(img.getUrl());
                        return imgDTO;
                    })
                    .collect(Collectors.toList());
            dto.setImages(imageDTOs);
        }

        return dto;
    }
}