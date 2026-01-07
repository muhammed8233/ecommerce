package com.example.ecommerce.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = Product.builder()
                .productName(request.getProductName())
                .sku(request.getSku())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();
       Product saved = productRepository.save(product);

       return ProductResponse.builder()
               .id(saved.getId())
               .productName(saved.getProductName())
               .description(saved.getDescription())
               .category(saved.getCategory())
               .sku(saved.getSku())
               .price(saved.getPrice())
               .stockQuantity(saved.getStockQuantity())
               .build();


    }
}
