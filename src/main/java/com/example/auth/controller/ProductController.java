package com.example.auth.controller;

import com.example.auth.dto.common.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @GetMapping()
    public ResponseEntity<ApiResponse<List<String>>> getProductList() {
        List<String> productList = Arrays.asList("Product1", "Product2", "Product3", "Product4");
        return ResponseEntity.ok(ApiResponse.success(productList));
    }
}
