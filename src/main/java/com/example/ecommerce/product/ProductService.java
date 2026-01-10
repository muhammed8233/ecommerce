package com.example.ecommerce.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {

     ProductResponse createProduct(ProductRequest request);

     ProductResponse updateProduct(Long productId, ProductRequest request);

     Product findProductById(Long productId);

     Page<ProductResponse> getProducts(String search, Pageable pageable);
}
