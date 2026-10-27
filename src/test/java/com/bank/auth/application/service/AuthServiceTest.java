package com.bank.auth.application.service;

import com.bank.auth.domain.model.User;
import com.bank.auth.domain.port.UserRepository;
import com.bank.auth.infrastructure.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @InjectMocks
    private AuthService authService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(
            UUID.randomUUID(),
            "testuser",
            "test@bank.com",
            "encodedPassword",
            true,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }

    @Test
    void authenticate_WithValidCredentials_ReturnsToken() {
        // Given: usuario existente con credenciales válidas
        when(userRepository.findByUsername("testuser"))
            .thenReturn(Optional.of(testUser));
        when(jwtTokenUtil.generateToken("testuser"))
            .thenReturn("jwt-token");

        // When: se autentican las credenciales
        String result = authService.authenticate("testuser", "password123");

        // Then: se obtiene un token válido
        assertNotNull(result);
        assertEquals("jwt-token", result);
    }

    @Test
    void authenticate_WithInvalidUsername_ReturnsNull() {
        // Given: usuario no existe
        when(userRepository.findByUsername("invalid"))
            .thenReturn(Optional.empty());

        // When: se intentan credenciales inválidas
        String result = authService.authenticate("invalid", "password");

        // Then: retorna null
        assertNull(result);
    }

    @Test
    void authenticate_WithInactiveUser_ReturnsNull() {
        // Given: usuario inactivo
        testUser.setActive(false);
        when(userRepository.findByUsername("testuser"))
            .thenReturn(Optional.of(testUser));

        // When: usuario inactivo intenta autenticarse
        String result = authService.authenticate("testuser", "password123");

        // Then: retorna null
        assertNull(result);
    }

    @Test
    void register_NewUser_CreatesSuccessfully() {
        // Given: nuevo usuario a registrar
        User newUser = new User(
            null,
            "newuser",
            "new@bank.com",
            "rawPassword",
            true,
            null,
            null
        );
        when(userRepository.findByUsername("newuser"))
            .thenReturn(Optional.empty());
        when(userRepository.findByEmail("new@bank.com"))
            .thenReturn(Optional.empty());
        when(userRepository.save(any(User.class)))
            .thenAnswer(invocation -> {
                User u = invocation.getArgument(0);
                u.setId(UUID.randomUUID());
                u.setCreatedAt(LocalDateTime.now());
                u.setUpdatedAt(LocalDateTime.now());
                return u;
            });

        // When: se registra el nuevo usuario
        User result = authService.register(newUser);

        // Then: usuario creado con ID asignado
        assertNotNull(result.getId());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_DuplicateUsername_ThrowsException() {
        // Given: usuario con username duplicado
        when(userRepository.findByUsername("testuser"))
            .thenReturn(Optional.of(testUser));

        // When/Then: lanzar excepción
        assertThrows(RuntimeException.class, () -> {
            authService.register(testUser);
        });
    }

    @Test
    void refreshToken_ValidToken_ReturnsNewToken() {
        // Given: token válido
        when(jwtTokenUtil.validateToken("old-token"))
            .thenReturn(true);
        when(jwtTokenUtil.getUsernameFromToken("old-token"))
            .thenReturn("testuser");
        when(jwtTokenUtil.generateToken("testuser"))
            .thenReturn("new-token");

        // When: se refresca el token
        String result = authService.refreshToken("old-token");

        // Then: retorna nuevo token
        assertNotNull(result);
        assertEquals("new-token", result);
    }

    @Test
    void logout_ValidToken_ClearsSession() {
        // Given: token activo
        when(jwtTokenUtil.validateToken("valid-token"))
            .thenReturn(true);

        // When: usuario hace logout
        authService.logout("valid-token");

        // Then: token invalidado
        verify(jwtTokenUtil).invalidateToken("valid-token");
    }
}