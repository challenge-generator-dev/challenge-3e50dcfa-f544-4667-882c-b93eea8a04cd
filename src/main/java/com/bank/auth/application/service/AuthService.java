package com.bank.auth.application.service;

import com.bank.auth.application.dto.AuthRequest;
import com.bank.auth.application.dto.AuthResponse;
import com.bank.auth.domain.model.User;
import com.bank.auth.domain.port.AuthServicePort;
import com.bank.auth.domain.port.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final AuthServicePort authServicePort;
    private final UserRepository userRepository;

    public AuthService(AuthServicePort authServicePort, UserRepository userRepository) {
        this.authServicePort = authServicePort;
        this.userRepository = userRepository;
    }

    public AuthResponse authenticate(AuthRequest request) {
        return null;
    }

    public AuthResponse register(AuthRequest request) {
        return null;
    }

    public String refreshToken(String refreshToken) {
        return null;
    }

    public void logout(String token) {
    }

    public User findUserById(UUID id) {
        return null;
    }
}