package com.bank.auth.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    public String extractUsername(String token) {
        return null;
    }

    public Date extractExpiration(String token) {
        return null;
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return null;
    }

    public String extractAllClaims(String token) {
        return null;
    }

    private Boolean isTokenExpired(String token) {
        return null;
    }

    public String generateToken(String username) {
        return null;
    }

    public String generateToken(String username, Map<String, Object> extraClaims) {
        return null;
    }

    public String generateRefreshToken(String username) {
        return null;
    }

    public Boolean validateToken(String token, String username) {
        return null;
    }

    public Boolean validateToken(String token) {
        return null;
    }
}