package com.example.auth.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignInRequest {
    @NotBlank
    @Size(min=8, max = 50)
    private String username;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}