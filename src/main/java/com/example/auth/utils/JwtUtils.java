package com.example.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;

@Component
public class JwtUtils {

    private static String secretKeyString;
    private static long accessTokenExpirationMs;
    private static long refreshTokenExpirationMs;

    @Value("${app.jwt.secret}")
    public void setSecretKeyString(String secret) {
        JwtUtils.secretKeyString = secret;
    }

    @Value("${app.jwt.accessTokenExpirationMs}")
    public void setAccessTokenExpirationMs(long accessTokenExpirationMs) {
        JwtUtils.accessTokenExpirationMs = accessTokenExpirationMs;
    }

    @Value("${app.jwt.refreshTokenExpirationMs}")
    public void setRefreshTokenExpirationMs(long refreshTokenExpirationMs) {
        JwtUtils.refreshTokenExpirationMs = refreshTokenExpirationMs;
    }

    private static SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    // Generate access token
    public static String generateAccessToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(getSecretKey())
                .compact();
    }

    // Generate refresh token
    public static String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(getSecretKey())
                .compact();
    }

    // Validate token
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSecretKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Extract user ID from token
    public static Long extractUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("userId", Long.class);
    }
}