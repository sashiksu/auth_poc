package com.example.auth.controller;

import com.example.auth.dto.common.ApiResponse;
import com.example.auth.dto.request.RefreshTokenRequest;
import com.example.auth.dto.request.SignInRequest;
import com.example.auth.dto.response.RefreshTokenResponse;
import com.example.auth.dto.response.SignInResponse;
import com.example.auth.dto.response.SignUpResponse;
import com.example.auth.entity.UserEntity;
import com.example.auth.dto.request.SignUpRequest;
import com.example.auth.service.UserService;
import com.example.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignUpResponse>> signUp(@RequestBody SignUpRequest signupPayload) {
        try {
            UserEntity newUser = userService.signUp(signupPayload);
            SignUpResponse signUpResponse = new SignUpResponse(newUser.getUsername());
            return ResponseEntity.ok(ApiResponse.success(signUpResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<SignInResponse>> signIn(@RequestBody SignInRequest signInPayload) {
        try {
            UserEntity user = userService.signIn(signInPayload);

            // generate access and refresh tokens
            String accessToken = JwtUtils.generateAccessToken(user.getUserId());
            String refreshToken = JwtUtils.generateRefreshToken(user.getUserId());

            SignInResponse signInResponse = new SignInResponse(accessToken, refreshToken, user.getUsername());
            return ResponseEntity.ok(ApiResponse.success(signInResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<RefreshTokenResponse>> refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        try {
            String refreshToken = refreshTokenRequest.getRefreshToken();

            // validate the refresh token
            if (!JwtUtils.validateToken(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Invalid refresh token"));
            }

            // extract the user ID from the refresh token
            Long userId = JwtUtils.extractUserId(refreshToken);

            // Get the user from the repository
            Optional<UserEntity> userOptional = userService.findById(userId);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("User not found"));
            }
            UserEntity user = userOptional.get();

            // generate new tokens
            String newAccessToken = JwtUtils.generateAccessToken(userId);
            String newRefreshToken = JwtUtils.generateRefreshToken(userId);

            // Create and return the response
            RefreshTokenResponse response = new RefreshTokenResponse(newAccessToken, newRefreshToken, user.getUsername());
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Failed to refresh token: " + e.getMessage()));
        }
    }
}
