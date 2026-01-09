package com.example.ecommerce.inventory;

public interface InventoryMovementService {
    void restockProduct(Long productId, int quantity);
    void deductStock(Long id, int quantity, Reason reason);
}
