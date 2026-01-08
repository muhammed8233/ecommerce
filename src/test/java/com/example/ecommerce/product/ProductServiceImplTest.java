package com.example.ecommerce.product;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ProductServiceImplTest {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Test
    void createProduct() {
        ProductRequest productRequest = ProductRequest.builder()
                .productName("bread")
                .category("medium")
                .description("family size")
                .price(new BigDecimal("2000"))
                .sku("BHM")
                .stockQuantity(1)
                .build();
        assertEquals(0, productRepository.findAll().size());
        productService.createProduct(productRequest);
        assertEquals(1, productRepository.findAll().size());
    }
}