package com.wasgames.wasgames.controller;

import com.wasgames.wasgames.BaseIntegrationTest;
import com.wasgames.wasgames.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CategoryControllerTest extends BaseIntegrationTest {

    @Test
    void testGetAllCategories_Success() throws Exception {
        String userToken = createUserToken();

        mockMvc.perform(get("/api/categories")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testCreateCategory_Success() throws Exception {
        String userToken = createUserToken();

        Category category = new Category();
        category.setName("New Category");

        mockMvc.perform(post("/api/categories")
                .header("Authorization", "Bearer " + userToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Category"));
    }

    @Test
    void testGetCategories_NoAuth_Forbidden() throws Exception {
        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreateCategory_AsAdmin_Success() throws Exception {
        String adminToken = createAdminToken();

        Category category = new Category();
        category.setName("Admin Category");

        mockMvc.perform(post("/api/categories")
                .header("Authorization", "Bearer " + adminToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Admin Category"));
    }
}