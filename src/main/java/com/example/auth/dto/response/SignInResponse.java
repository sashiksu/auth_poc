package com.example.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SignInResponse {

    private String accessToken;
    private String refreshToken;
    private String username;
    // private List<String> roles;

    public SignInResponse(String accessToken, String refreshToken, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }
}
