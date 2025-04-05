package com.example.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;


@Getter
@Setter
public class ProductRequest {
    @NotBlank
    @Size(min = 8, max = 50)
    private String name;

    @NotBlank
    @Size(min = 1, max = 6)
    private BigDecimal price;
}