package com.example.ecommerce.order;


import org.springframework.data.domain.Page;

import java.awt.print.Pageable;

public interface OrderService {
    String placeOrderAndInitiatePayment(OrderRequest request);

    OrderResponse placeOrder(OrderRequest request);

    void finalizeTransaction(String reference);

    Page<OrderResponse> getOrders(String search, Pageable pageable);
}
