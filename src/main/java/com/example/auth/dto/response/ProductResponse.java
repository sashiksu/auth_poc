package com.example.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private BigDecimal price;
    private Long created_by;

    public ProductResponse(Long id, String name, BigDecimal price, Long created_by) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.created_by = created_by;
    }
}
