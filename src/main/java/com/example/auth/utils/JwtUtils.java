package com.example.auth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;

public class JwtUtils {

    // Use a properly sized key - this is just an example, don't use this in production!
    private static final String SECRET_KEY_STRING = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    // Convert the hex-encoded string to a proper SecretKey
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            Decoders.BASE64.decode(SECRET_KEY_STRING)
    );

    // Generate access token
    public static String generateAccessToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 15 * 60 * 1000)) // 15 minutes
                .signWith(SECRET_KEY)
                .compact();
    }

    // Generate refresh token
    public static String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 7 days
                .signWith(SECRET_KEY)
                .compact();
    }

    // Validate token
    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(SECRET_KEY)
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
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get("userId", Long.class);
    }
}