package com.bank.auth.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthResponse(
    @JsonProperty("access_token")
    String accessToken,
    
    @JsonProperty("token_type")
    String tokenType,
    
    @JsonProperty("expires_in")
    long expiresIn,
    
    @JsonProperty("username")
    String username
) {
    public static AuthResponse of(String accessToken, long expiresIn, String username) {
        return new AuthResponse(accessToken, "Bearer", expiresIn, username);
    }
}