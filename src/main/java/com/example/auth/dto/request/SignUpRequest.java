package com.example.auth.dto.request;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpRequest {
 @NotBlank
 @Size(min=8, max = 50)
 private String username;

 @NotBlank
 @Size(min = 6, max = 40)
 private String password;

 @NotBlank
 @Size(max = 100)
 private String firstName;

 @NotBlank
 @Size(max = 100)
 private String lastName;
}
