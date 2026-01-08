package com.example.ecommerce.inventory;

import com.example.ecommerce.product.Product;
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
    @Override
    public Product restockProduct(Long productId, int quantity) {
        return null;
    }
}
