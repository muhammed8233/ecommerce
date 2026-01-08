package com.example.ecommerce.product;

import com.example.ecommerce.inventory.InventoryMovementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.autoconfigure.web.DataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<Page<ProductResponse>> listProducts(@RequestParam String search,
                                                                    @PageableDefault(size = 10,sort = "productName")Pageable pageable){
        return ResponseEntity.ok(productService.getProducts(search, pageable));
    }
    @PostMapping("/create")
    public  ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("studentId") Long productId, @Valid
                                                         @RequestBody ProductRequest request){
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    @PutMapping("/{productId}/restock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> restockProduct(@PathVariable Long productId,
                                                  @RequestParam int quantity){
        inventoryMovementService.restockProduct(productId, quantity);
        return ResponseEntity.ok("product restock successfully");
    }
}
