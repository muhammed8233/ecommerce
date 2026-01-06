package com.example.ecommerce.product;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "product name can not be empty")
    @Size(min = 2, message = "product name size must be > 2")
    @Column(nullable = false)
    private String productName;

    @NotBlank(message = "description can not be empty")
    @Column(length = 1000)
    private String description;

    @NotBlank(message = "sku can not be empty")
    @Column(nullable = false, unique = true)
    private String sku;

    @PositiveOrZero(message = "price must be 0 or 1")
    @Column(nullable = false, precision = 10,scale = 2)
    private BigDecimal price;

    @PositiveOrZero(message = "stock quantity must be 0 or 1")
    private int stockQuantity;

    @NotBlank(message = "category can not be empty")
    private String category;

    @Version
    private int version;

}
