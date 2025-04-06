package com.example.auth.utils;

import com.example.auth.config.jwt.AuthInfoInstance;
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

    // generate access token
    public static String generateAccessToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("parentId",123)
                .claim("childId",321)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpirationMs))
                .signWith(getSecretKey())
                .compact();
    }

    // generate refresh token
    public static String generateRefreshToken(Long userId) {
        return Jwts.builder()
                .claim("userId", userId)
                .claim("parentId",123)
                .claim("childId",321)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationMs))
                .signWith(getSecretKey())
                .compact();
    }

    // validate token
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

    // extract auth info
    public static AuthInfoInstance extractAuthInfo(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        AuthInfoInstance authInfo = new AuthInfoInstance();
        authInfo.setUserId(claims.get("userId", Long.class));
        authInfo.setParentId(claims.get("parentId", Long.class));
        authInfo.setChildId(claims.get("childId", Long.class));

        return authInfo;
    }

}