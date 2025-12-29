package com.wasgames.wasgames.controller;

import com.wasgames.wasgames.BaseIntegrationTest;
import com.wasgames.wasgames.dto.LoginDTO;
import com.wasgames.wasgames.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AuthenticationControllerTest extends BaseIntegrationTest {

    @Test
    void testRegister_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newuser");
        userDTO.setEmail("newuser@test.com");
        userDTO.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered"));
    }

    @Test
    void testRegisterAdmin_Success() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("newadmin");
        userDTO.setEmail("newadmin@test.com");
        userDTO.setPassword("admin123");

        mockMvc.perform(post("/api/auth/register-admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("Admin registered"));
    }

    @Test
    void testLogin_Success() throws Exception {
        // Сначала создаём пользователя
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("logintest");
        userDTO.setEmail("logintest@test.com");
        userDTO.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        // Теперь логинимся
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("logintest");
        loginDTO.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.not("")));
    }

    @Test
    void testLogin_InvalidCredentials() throws Exception {
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setLogin("nonexistent");
        loginDTO.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isUnauthorized());
    }

        @Test
        void testRegister_DuplicateUsername() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("duplicate");
        userDTO.setEmail("duplicate1@test.com");
        userDTO.setPassword("password123");

        // Первая регистрация
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk());

        // Вторая регистрация с тем же username
        userDTO.setEmail("duplicate2@test.com");
        mockMvc.perform(post("/api/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username already exists"));
        }
}