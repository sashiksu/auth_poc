package com.example.auth.config;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Long userId;

    public JwtAuthenticationToken(Long userId) {
        super(null); // No authorities yet
        this.userId = userId;
        setAuthenticated(true); // We trust this token is valid once parsed
    }

    @Override
    public Object getCredentials() {
        return null; // No credentials are needed
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }
}
