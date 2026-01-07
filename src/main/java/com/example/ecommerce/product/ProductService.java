package com.example.ecommerce.product;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;

public interface ProductService {

     ProductResponse createProduct(ProductRequest request);
}
