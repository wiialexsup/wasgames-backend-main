package com.wasgames.wasgames.controller;

import com.wasgames.wasgames.BaseIntegrationTest;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerTest extends BaseIntegrationTest {

    @Test
    void testGetAllUsers_Success() throws Exception {
        String userToken = createUserToken();

        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetAllUsers_NoAuth_Forbidden() throws Exception {
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isForbidden());
    }

    @Test
    void testGetAllUsers_AsAdmin_Success() throws Exception {
        String adminToken = createAdminToken();

        mockMvc.perform(get("/api/users")
                .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}