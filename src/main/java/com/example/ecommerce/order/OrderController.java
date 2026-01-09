package com.example.ecommerce.order;

import com.example.ecommerce.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;

}
