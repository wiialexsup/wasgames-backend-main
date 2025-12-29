package com.wasgames.wasgames.controller;

import com.wasgames.wasgames.BaseIntegrationTest;
import com.wasgames.wasgames.dto.CategoryDTO;
import com.wasgames.wasgames.dto.ProductDTO;
import com.wasgames.wasgames.dto.ImageDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AdminControllerTest extends BaseIntegrationTest {

    @Test
    void testCreateProduct_AsAdmin_Success() throws Exception {
        String adminToken = createAdminToken();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Game");
        productDTO.setDescription("Test Description");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);
        productDTO.setMinPlayers(2);
        productDTO.setMaxPlayers(4);
        productDTO.setAgeLimit(12);
        productDTO.setPlayTimeMinutes(60);

        mockMvc.perform(post("/api/admin/products")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Game"))
                .andExpect(jsonPath("$.price").value(29.99));
    }

    @Test
    void testCreateProduct_AsUser_Forbidden() throws Exception {
        String userToken = createUserToken();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Game");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);

        mockMvc.perform(post("/api/admin/products")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreateProduct_NoAuth_Forbidden() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Game");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);

        mockMvc.perform(post("/api/admin/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testUpdateProduct_Success() throws Exception {
        String adminToken = createAdminToken();

        // Сначала создаём продукт
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Original Name");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);

        String response = mockMvc.perform(post("/api/admin/products")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO created = objectMapper.readValue(response, ProductDTO.class);

        // Теперь обновляем
        created.setName("Updated Name");
        created.setPrice(new BigDecimal("39.99"));

        mockMvc.perform(put("/api/admin/products/" + created.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Name"))
                .andExpect(jsonPath("$.price").value(39.99));
    }

    @Test
    void testUpdateStock_Success() throws Exception {
        String adminToken = createAdminToken();

        // Создаём продукт
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Stock Test");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);

        String response = mockMvc.perform(post("/api/admin/products")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO created = objectMapper.readValue(response, ProductDTO.class);

        // Обновляем stock
        mockMvc.perform(patch("/api/admin/products/" + created.getId() + "/stock")
                .header("Authorization", "Bearer " + adminToken)
                .param("stock", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock").value(50));
    }

    @Test
    void testUpdatePrice_Success() throws Exception {
        String adminToken = createAdminToken();

        // Создаём продукт
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Price Test");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);

        String response = mockMvc.perform(post("/api/admin/products")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO created = objectMapper.readValue(response, ProductDTO.class);

        // Обновляем price
        mockMvc.perform(patch("/api/admin/products/" + created.getId() + "/price")
                .header("Authorization", "Bearer " + adminToken)
                .param("price", "49.99"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(49.99));
    }

    @Test
    void testDeleteProduct_Success() throws Exception {
        String adminToken = createAdminToken();

        // Создаём продукт
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Delete Test");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);

        String response = mockMvc.perform(post("/api/admin/products")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO created = objectMapper.readValue(response, ProductDTO.class);

        // Удаляем продукт
        mockMvc.perform(delete("/api/admin/products/" + created.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateCategory_Success() throws Exception {
        String adminToken = createAdminToken();

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Strategy Games");

        mockMvc.perform(post("/api/admin/categories")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Strategy Games"));
    }

    @Test
    void testUpdateCategory_Success() throws Exception {
        String adminToken = createAdminToken();

        // Создаём категорию
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Original Category");

        String response = mockMvc.perform(post("/api/admin/categories")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CategoryDTO created = objectMapper.readValue(response, CategoryDTO.class);

        // Обновляем
        created.setName("Updated Category");

        mockMvc.perform(put("/api/admin/categories/" + created.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(created)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Category"));
    }

    @Test
    void testDeleteCategory_Success() throws Exception {
        String adminToken = createAdminToken();

        // Создаём категорию
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Delete Category");

        String response = mockMvc.perform(post("/api/admin/categories")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        CategoryDTO created = objectMapper.readValue(response, CategoryDTO.class);

        // Удаляем
        mockMvc.perform(delete("/api/admin/categories/" + created.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllCategories_Success() throws Exception {
        String adminToken = createAdminToken();

        mockMvc.perform(get("/api/admin/categories")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetAllProducts_Success() throws Exception {
        String adminToken = createAdminToken();

        mockMvc.perform(get("/api/admin/products/all")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetLowStockProducts_Success() throws Exception {
        String adminToken = createAdminToken();

        mockMvc.perform(get("/api/admin/products/low-stock")
                .header("Authorization", "Bearer " + adminToken)
                .param("threshold", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testAddImage_Success() throws Exception {
        String adminToken = createAdminToken();

        // Создаём продукт
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Image Test");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);

        String response = mockMvc.perform(post("/api/admin/products")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO created = objectMapper.readValue(response, ProductDTO.class);

        // Добавляем изображение
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUrl("https://example.com/image.jpg");

        mockMvc.perform(post("/api/admin/products/" + created.getId() + "/images")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(imageDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.url").value("https://example.com/image.jpg"));
    }

    @Test
    void testUpdateImage_Success() throws Exception {
        String adminToken = createAdminToken();

        // Создаём продукт
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Image Update Test");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);

        String productResponse = mockMvc.perform(post("/api/admin/products")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO createdProduct = objectMapper.readValue(productResponse, ProductDTO.class);

        // Добавляем изображение
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUrl("https://example.com/old-image.jpg");

        String imageResponse = mockMvc.perform(post("/api/admin/products/" + createdProduct.getId() + "/images")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(imageDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ImageDTO createdImage = objectMapper.readValue(imageResponse, ImageDTO.class);

        // Обновляем изображение
        createdImage.setUrl("https://example.com/new-image.jpg");

        mockMvc.perform(put("/api/admin/images/" + createdImage.getId())
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createdImage)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.url").value("https://example.com/new-image.jpg"));
    }

    @Test
    void testDeleteImage_Success() throws Exception {
        String adminToken = createAdminToken();

        // Создаём продукт
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Image Delete Test");
        productDTO.setPrice(new BigDecimal("29.99"));
        productDTO.setStock(10);

        String productResponse = mockMvc.perform(post("/api/admin/products")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ProductDTO createdProduct = objectMapper.readValue(productResponse, ProductDTO.class);

        // Добавляем изображение
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setUrl("https://example.com/delete-image.jpg");

        String imageResponse = mockMvc.perform(post("/api/admin/products/" + createdProduct.getId() + "/images")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(imageDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ImageDTO createdImage = objectMapper.readValue(imageResponse, ImageDTO.class);

        // Удаляем изображение
        mockMvc.perform(delete("/api/admin/images/" + createdImage.getId())
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }
}