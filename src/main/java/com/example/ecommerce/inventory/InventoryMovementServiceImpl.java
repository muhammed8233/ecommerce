package com.example.ecommerce.inventory;

import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductRepository;
import com.example.ecommerce.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryMovementServiceImpl implements InventoryMovementService{

    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductRepository productRepository;


    @Override
    public void restockProduct(Long productId, int quantity) {
        Product product = productService.findProductById(productId);

        product.setStockQuantity(product.getStockQuantity() + quantity);
        productRepository.save(product);

        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .quantityChange(quantity)
                .reason(Reason.RESTOCK)
                .build();

        inventoryMovementRepository.save(movement);
    }

    @Override
    public void deductStock(Long productId, int quantity, Reason reason) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);

        InventoryMovement movement = InventoryMovement.builder()
                .product(product)
                .quantityChange(-quantity)
                .reason(reason)
                .build();

        inventoryMovementRepository.save(movement);
    }
}
