package com.example.ecommerce.inventory;

import com.example.ecommerce.order.OrderItem;
import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;

public interface InventoryMovementService {
    void restockProduct(Long productId, int quantity);
    void deductStock(OrderItem orderItem);

}
