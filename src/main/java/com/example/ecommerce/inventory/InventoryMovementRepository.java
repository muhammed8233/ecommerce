package com.example.ecommerce.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    int getStock(Long productId);
}
