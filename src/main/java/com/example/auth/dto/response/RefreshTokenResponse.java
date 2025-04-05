

package com.example.auth.dto.response;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RefreshTokenResponse {

    private String accessToken;
    private String refreshToken;
    private String username;

    public RefreshTokenResponse(String accessToken, String refreshToken, String username) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
    }
}
