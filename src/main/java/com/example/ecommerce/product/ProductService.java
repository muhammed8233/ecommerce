package com.example.ecommerce.product;

import java.util.List;

public interface ProductService {

     Product createProduct(ProductRequest request);

     Product updateProduct(Long productId, ProductRequest request);

     List<Product> getAllProduct();

}
