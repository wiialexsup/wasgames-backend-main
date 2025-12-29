package com.wasgames.wasgames.controller;

import com.wasgames.wasgames.BaseIntegrationTest;
import com.wasgames.wasgames.dto.ProductDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerTest extends BaseIntegrationTest {

    @Test
    void testGetAllProducts_Success() throws Exception {
        String userToken = createUserToken();

        mockMvc.perform(get("/api/products")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetProductById_Success() throws Exception {
        String adminToken = createAdminToken();
        String userToken = createUserToken();

        // СНАЧАЛА СОЗДАЁМ ПРОДУКТ
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Test Product");
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

        // ТЕПЕРЬ ПОЛУЧАЕМ ЕГО
        mockMvc.perform(get("/api/products/" + created.getId())
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(created.getId()));
    }

    @Test
    void testGetProductById_NotFound() throws Exception {
        String userToken = createUserToken();

        mockMvc.perform(get("/api/products/999999")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isNotFound()); // ИЗМЕНЕНО: ожидаем 404
    }

    @Test
    void testSearchProducts_Success() throws Exception {
        String userToken = createUserToken();

        mockMvc.perform(get("/api/products/search")
                .header("Authorization", "Bearer " + userToken)
                .param("name", "game"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetProductsByCategory_Success() throws Exception {
        String userToken = createUserToken();

        mockMvc.perform(get("/api/products/category/1")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetProducts_NoAuth_Forbidden() throws Exception {
        mockMvc.perform(get("/api/products"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetProducts_AsAdmin_Success() throws Exception {
        String adminToken = createAdminToken();

        mockMvc.perform(get("/api/products")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}