package com.example.auth.controller;

import com.example.auth.dto.common.ApiResponse;
import com.example.auth.dto.request.SignInRequest;
import com.example.auth.dto.response.SignInResponse;
import com.example.auth.dto.response.SignUpResponse;
import com.example.auth.entity.UserEntity;
import com.example.auth.dto.request.SignUpRequest;
import com.example.auth.service.UserService;
import com.example.auth.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // write logic for refresh token
}
