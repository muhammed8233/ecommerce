package com.example.ecommerce.inventory;

import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductRepository;
import com.example.ecommerce.product.ProductResponse;
import com.example.ecommerce.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceImpl implements InventoryMovementService{

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;

    @Autowired

    @Override
    public ProductResponse restockProduct(Long productId, int quantity) {
        Product product = productService.findProductById(productId);

        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);

        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .quantityChange(quantity)
                .reason("Manually restock by admin")
                .createdAt(LocalDate.now())
                .build();

        inventoryMovementRepository.save(movement);
    }
}
