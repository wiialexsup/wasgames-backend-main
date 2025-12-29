package com.wasgames.wasgames.service;

import com.wasgames.wasgames.dto.ImageDTO;
import com.wasgames.wasgames.model.Image;

import java.util.List;
import java.util.Optional;

public interface ImageService {
    Image save(Image image);
    Optional<Image> findById(Long id);
    List<Image> findAll();
    void deleteById(Long id);

    ImageDTO addImageToProduct(Long productId, ImageDTO imageDTO);
    void deleteImage(Long imageId);
    ImageDTO updateImage(Long imageId, ImageDTO imageDTO);
    List<ImageDTO> getImagesByProductId(Long productId);
}