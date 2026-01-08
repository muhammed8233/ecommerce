package com.example.ecommerce.product;

import com.example.ecommerce.exception.ProductNotFoundException;
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

       return mapToProductResponse(saved);

    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setProductName(request.getProductName());
        product.setCategory(request.getCategory());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setSku(request.getSku());
        product.setStockQuantity(request.getStockQuantity());

        Product updateProduct = productRepository.save(product);

        return mapToProductResponse(updateProduct);

    }

    @Override
    public List<ProductResponse> getAllProduct() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(()-> new ProductNotFoundException("product with id"+productId+ "not found"));
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .category(product.getCategory())
                .sku(product.getSku())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .build();
    }

}
