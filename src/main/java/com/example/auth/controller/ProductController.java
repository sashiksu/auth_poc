package com.example.auth.controller;

import com.example.auth.dto.common.ApiResponse;
import com.example.auth.dto.request.ProductRequest;
import com.example.auth.dto.request.SignUpRequest;
import com.example.auth.dto.response.ProductResponse;
import com.example.auth.dto.response.SignUpResponse;
import com.example.auth.entity.ProductEntity;
import com.example.auth.entity.UserEntity;
import com.example.auth.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getProductList() {
        try {
            List<ProductEntity> products = productService.getAllProducts();
            List<ProductResponse> productResponses = products.stream()
                    .map(product -> new ProductResponse(
                            product.getProduct_id(),
                            product.getName(),
                            product.getPrice(),
                            product.getCreated_by()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(ApiResponse.success(productResponses));

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest productRequest) {
        try {
            ProductEntity newProduct = productService.createProduct(productRequest);
            ProductResponse productResponse = new ProductResponse(newProduct.getProduct_id(), newProduct.getName(), newProduct.getPrice(), newProduct.getCreated_by());
            return ResponseEntity.ok(ApiResponse.success(productResponse));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }
}
