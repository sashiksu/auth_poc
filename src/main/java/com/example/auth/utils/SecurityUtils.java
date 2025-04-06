package com.example.auth.utils;

import com.example.auth.config.jwt.AuthInfoInstance;
import com.example.auth.config.jwt.JwtAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
public class SecurityUtils {

    public AuthInfoInstance getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new IllegalStateException("User not authenticated");
        }

        if (authentication instanceof JwtAuthenticationToken jwtAuth) {
            return jwtAuth.getAuthInfo();
        } else {
            throw new IllegalStateException("Unexpected authentication type");
        }
    }

}