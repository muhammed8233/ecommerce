package com.example.ecommerce.product;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product createProduct(ProductRequest request) {
        Product product = Product.builder()
                .productName(request.getProductName())
                .sku(request.getSku())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .build();
       Product saved = productRepository.save(product);

       return Product.builder()
               .productId(saved.getId())
               .productName(saved.getProductName())
               .description(saved.getDescription())
               .category(saved.getCategory())
               .sku(saved.getSku())
               .price(saved.getPrice())
               .stockQuantity(saved.getStockQuantity())
               .build();


    }

    @Override
    public Product updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setProductName(request.getProductName());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setStockQuantity(request.getStockQuantity());

        Product updateProduct = productRepository.save(product);

        return Product.builder()
                .productId(updateProduct.getId())
                .productName(updateProduct.getProductName())
                .category(updateProduct.getCategory())
                .description(updateProduct.getDescription())
                .price(updateProduct.getPrice())
                .sku(updateProduct.getSku())
                .stockQuantity(updateProduct.getStockQuantity())
                .build();



    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll()
                .stream().map(this::mapToProductResponse.collect(Collectors.toList());
    }

    private Product mapToProductResponse(Product product) {
        return Product.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .category(product.getCategory())
                .sku(product.getSku())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .build();
    }

}
