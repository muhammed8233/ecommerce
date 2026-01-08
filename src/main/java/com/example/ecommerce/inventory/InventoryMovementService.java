package com.example.ecommerce.inventory;

import com.example.ecommerce.product.Product;

public interface InventoryMovementService {
    Product restockProduct(Long productId, int quantity);

}
