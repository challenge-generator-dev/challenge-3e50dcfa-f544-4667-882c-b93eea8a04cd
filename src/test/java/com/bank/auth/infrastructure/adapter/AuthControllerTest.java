package com.bank.auth.infrastructure.adapter;

import com.bank.auth.application.dto.AuthRequest;
import com.bank.auth.application.dto.AuthResponse;
import com.bank.auth.application.service.AuthService;
import com.bank.auth.domain.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @Test
    void login_WithValidCredentials_ReturnsOk() throws Exception {
        // Given: credenciales válidas
        AuthRequest request = new AuthRequest("testuser", "password123");
        AuthResponse response = new AuthResponse("jwt-token", "refresh-token");
        
        when(authService.authenticate("testuser", "password123"))
            .thenReturn("jwt-token");

        // When/Then: login exitoso
        mockMvc.perform(post("/api/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    @Test
    void login_WithInvalidCredentials_ReturnsUnauthorized() throws Exception {
        // Given: credenciales inválidas
        AuthRequest request = new AuthRequest("invalid", "wrongpassword");
        
        when(authService.authenticate(anyString(), anyString()))
            .thenReturn(null);

        // When/Then: login fallido
        mockMvc.perform(post("/api/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized());
    }

    @Test
    void register_WithValidData_ReturnsCreated() throws Exception {
        // Given: datos de registro válidos
        AuthRequest request = new AuthRequest("newuser", "password123");
        User newUser = new User(
            UUID.randomUUID(),
            "newuser",
            "newuser@bank.com",
            "encodedPassword",
            true,
            LocalDateTime.now(),
            LocalDateTime.now()
        );

        when(authService.register(any(User.class)))
            .thenReturn(newUser);

        // When/Then: registro exitoso
        mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void refreshToken_WithValidToken_ReturnsNewToken() throws Exception {
        // Given: token de refresco válido
        when(authService.refreshToken("valid-refresh-token"))
            .thenReturn("new-jwt-token");

        // When/Then: refresco exitoso
        mockMvc.perform(post("/api/auth/refresh")
                .with(csrf())
                .param("token", "valid-refresh-token"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("new-jwt-token"));
    }

    @Test
    @WithMockUser(username = "testuser")
    void logout_WithAuthenticatedUser_ReturnsOk() throws Exception {
        // Given: usuario autenticado
        // When/Then: logout exitoso
        mockMvc.perform(post("/api/auth/logout")
                .with(csrf()))
            .andExpect(status().isOk());
    }

    @Test
    void login_MissingBody_ReturnsBadRequest() throws Exception {
        // Given: cuerpo de petición vacío
        // When/Then: error de validación
        mockMvc.perform(post("/api/auth/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
            .andExpect(status().isBadRequest());
    }

    @Test
    void register_InvalidEmail_ReturnsBadRequest() throws Exception {
        // Given: email inválido
        AuthRequest request = new AuthRequest("user", "password");
        request.setEmail("invalid-email");

        // When/Then: error de validación
        mockMvc.perform(post("/api/auth/register")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest());
    }
}