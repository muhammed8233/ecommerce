package com.example.ecommerce.inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {
    int countById(Long productId);
}


