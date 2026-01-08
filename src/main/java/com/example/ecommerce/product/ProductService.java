package com.example.ecommerce.product;

import java.util.List;

public interface ProductService {

     ProductResponse createProduct(ProductRequest request);

     ProductResponse updateProduct(Long productId, ProductRequest request);

     List<ProductResponse> getAllProduct();

    Product findProductById(Long productId);
}
