package com.example.ecommerce.order;

import com.example.ecommerce.payment.PaymentGatewayService;
import com.example.ecommerce.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


public interface OrderService {
    String placeOrderAndInitiatePayment(OrderRequest request);

    OrderResponse placeOrder(OrderRequest request);
}
