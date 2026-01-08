package com.example.ecommerce.product;

import com.example.ecommerce.inventory.InventoryMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryMovementService inventoryMovementService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProduct(){
        return ResponseEntity.ok(productService.getAllProduct());
    }
    @PostMapping("/create")
    public  ResponseEntity<Product> createProduct(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@PathVariable("studentId") Long productId, @Valid
                                                         @RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    @PutMapping("/{productId}/restock")
    public ResponseEntity<Product> restockProduct(@PathVariable Long productId,
                                                  @RequestParam int quantity){
        return ResponseEntity.ok(inventoryMovementService.restockProduct(productId, quantity));
    }
}
