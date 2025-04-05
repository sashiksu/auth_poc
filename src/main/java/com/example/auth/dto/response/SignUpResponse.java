package com.example.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponse {
    private String username;

    public SignUpResponse(String username) {
        this.username = username;
    }
}
