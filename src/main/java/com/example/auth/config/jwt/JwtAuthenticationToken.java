package com.example.auth.config.jwt;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final AuthInfoInstance authInfo;

    public JwtAuthenticationToken(AuthInfoInstance authInfo) {
        super(null); // no authorities yet
        this.authInfo = authInfo;
        setAuthenticated(true); // trust this token is valid once parsed
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return authInfo;
    }
}
