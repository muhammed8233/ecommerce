package com.example.ecommerce.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
public class OrderResponse {
    int orderId;
    String productName;
    int quantity;
    BigDecimal unitPrice;
    BigDecimal totalAmount;
    String status;
    LocalDateTime createdAt;
    String trackingNumber;
}
