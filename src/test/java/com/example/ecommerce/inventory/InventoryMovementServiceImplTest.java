package com.example.ecommerce.inventory;

import com.example.ecommerce.product.ProductRequest;
import com.example.ecommerce.product.ProductResponse;
import com.example.ecommerce.product.ProductService;
import jakarta.transaction.Transactional;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class InventoryMovementServiceImplTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryMovementService inventoryMovementService;
    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;

//    @Test
//    void restockProduct() {
//        ProductRequest productRequest = ProductRequest.builder()
//                .productName("bread")
//                .category("medium")
//                .description("family size")
//                .price(new BigDecimal("2000"))
//                .sku("BHM")
//                .stockQuantity(1)
//                .build();
//       productService.createProduct(productRequest);
//       List<ProductResponse> saved = productService.listProduct();
//      assertEquals(0,inventoryMovementRepository.findAll().size());
//      inventoryMovementService.restockProduct(saved.getFirst().getProductId(), 20);
//        assertEquals(1,inventoryMovementRepository.findAll().size());
//
//    }
}