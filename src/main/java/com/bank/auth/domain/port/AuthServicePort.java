package com.bank.auth.domain.port;

import com.bank.auth.domain.model.User;

public interface AuthServicePort {
    String authenticate(String username, String password);
    String refreshToken(String refreshToken);
    User register(User user);
    void logout(String token);
}