package com.example.ecommerce.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String productName;
    private String sku;
    private String description;
    private String category;
    private BigDecimal price;
    private int stockQuantity;
    private boolean isOutOfStock;
}
