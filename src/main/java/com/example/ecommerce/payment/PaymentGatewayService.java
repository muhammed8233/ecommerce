package com.example.ecommerce.payment;

import java.math.BigDecimal;

public interface PaymentGatewayService {

    String initiatePayment(BigDecimal amount, String currency, String orderId);
    PaymentStatus checkPaymentStatus(String referenceId);

}
