package com.example.ecommerce.product;

import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

     ProductResponse createProduct(ProductRequest request);

     ProductResponse updateProduct(Long productId, ProductRequest request);

//     List<ProductResponse> getAllProduct();

     Product findProductById(Long productId);

     Page<ProductResponse> getProducts(String search, Pageable pageable);
}
