package com.example.auth.dto.common;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApiResponse<T> {
    // Getters and setters
    private String error;
    private T data;

    // success response constructor
    public ApiResponse(T data) {
        this.error = null;
        this.data = data;
    }

    // error response constructor
    public ApiResponse(String error) {
        this.error = error;
        this.data = null;
    }

    // static factory methods for convenience
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }

    public static <T> ApiResponse<T> error(String errorMessage) {
        return new ApiResponse<>(errorMessage);
    }

}