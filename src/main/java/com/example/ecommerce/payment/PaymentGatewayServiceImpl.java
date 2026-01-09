package com.example.ecommerce.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentGatewayServiceImpl implements PaymentGatewayService{

    private final PaymentRepository paymentRepository;

    @Override
    public String initiatePayment(BigDecimal amount, String currency, String orderId) {
        return "FAKE_REF_" + System.currentTimeMillis();
    }

    @Override
    public PaymentStatus checkPaymentStatus(String referenceId) {
        return PaymentStatus.SUCCESS;

    }

    @Override
    public Payment findByReference(String reference) {
        return paymentRepository.findByReference(reference);
    }


}