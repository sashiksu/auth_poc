package com.example.auth.service;

import com.example.auth.config.jwt.AuthInfoInstance;
import com.example.auth.dto.request.ProductRequest;
import com.example.auth.entity.ProductEntity;
import com.example.auth.repository.ProductRepository;
import com.example.auth.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SecurityUtils securityUtils;

    public ProductEntity createProduct(ProductRequest productRequest) {
        try {
            String productName = productRequest.getName();
            BigDecimal productPrice = productRequest.getPrice();
            AuthInfoInstance authInfo = securityUtils.getCurrentUser();

            ProductEntity newProduct = new ProductEntity(productName, productPrice, authInfo.getUserId());
            return productRepository.save(newProduct);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create product: " + e.getMessage(), e);
        }
    }

    public List<ProductEntity> getAllProducts() {
        try {
            return productRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch products: " + e.getMessage(), e);
        }
    }
}
