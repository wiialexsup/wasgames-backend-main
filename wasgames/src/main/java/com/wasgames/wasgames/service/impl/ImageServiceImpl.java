package com.wasgames.wasgames.service.impl;

import com.wasgames.wasgames.dto.ImageDTO;
import com.wasgames.wasgames.model.Image;
import com.wasgames.wasgames.model.Product;
import com.wasgames.wasgames.repository.ImageRepository;
import com.wasgames.wasgames.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wasgames.wasgames.service.ImageService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    @Override
    @Transactional
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    @Transactional
    public ImageDTO addImageToProduct(Long productId, ImageDTO imageDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Image image = new Image();
        image.setUrl(imageDTO.getUrl());
        image.setProduct(product);

        Image saved = imageRepository.save(image);
        return mapEntityToDto(saved);
    }

    @Override
    @Transactional
    public void deleteImage(Long imageId) {
        if (!imageRepository.existsById(imageId)) {
            throw new RuntimeException("Image not found with id: " + imageId);
        }
        imageRepository.deleteById(imageId);
    }

    @Override
    @Transactional
    public ImageDTO updateImage(Long imageId, ImageDTO imageDTO) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found with id: " + imageId));
        
        image.setUrl(imageDTO.getUrl());
        Image updated = imageRepository.save(image);
        
        return mapEntityToDto(updated);
    }

    @Override
    public List<ImageDTO> getImagesByProductId(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        
        return product.getImages().stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private ImageDTO mapEntityToDto(Image entity) {
        ImageDTO dto = new ImageDTO();
        dto.setId(entity.getId());
        dto.setUrl(entity.getUrl());
        return dto;
    }
}