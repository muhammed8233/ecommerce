package com.example.ecommerce.order;

import com.example.ecommerce.exception.InsufficientStockException;
import com.example.ecommerce.exception.PaymentNotFoundException;
import com.example.ecommerce.exception.ProductNotFoundException;
import com.example.ecommerce.inventory.InventoryMovementRepository;
import com.example.ecommerce.inventory.InventoryMovementService;
import com.example.ecommerce.inventory.Reason;
import com.example.ecommerce.payment.Payment;
import com.example.ecommerce.payment.PaymentGatewayService;
import com.example.ecommerce.payment.PaymentRepository;
import com.example.ecommerce.payment.PaymentStatus;
import com.example.ecommerce.product.Product;
import com.example.ecommerce.product.ProductRepository;
import com.example.ecommerce.user.User;
import com.example.ecommerce.user.UserRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentGatewayService paymentGatewayService;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private InventoryMovementRepository inventoryMovementRepository;
    @Autowired
    private InventoryMovementService inventoryMovementService;

    @Override
    public String placeOrderAndInitiatePayment(OrderRequest request) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        BigDecimal totalAmount = calculateTotal(request);

        Order order = savePendingOrder(request, user, totalAmount);

         savedOrderItems(request, order);

        String reference = paymentGatewayService.initiatePayment(
                order.getTotalAmount(), "USD", order.getId().toString());

        Payment payment = Payment.builder()
                .order(order)
                .reference(reference)
                .status(PaymentStatus.PENDING)
                .build();
        paymentRepository.save(payment);

        return reference;
    }

    private void savedOrderItems(OrderRequest request, Order order) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(()-> new ProductNotFoundException("product not found"));

        OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(request.getQuantity())
                .unitPrice(product.getPrice())
                .build();
    }

    private Order savePendingOrder(OrderRequest request, User user, BigDecimal totalAmount) {

        Order order = Order.builder()
                .user(user)
                .status(Status.PENDING)
                .totalAmount(totalAmount)
                .build();

        request.getItemList().stream()
                .map(orderItemRequest -> {
                    Product product = productRepository.findById(orderItemRequest.getProductId())
                            .orElseThrow(() -> new ProductNotFoundException("Product not found: " + request.getProductId()));


                    return OrderItem.builder()
                            .product(product)
                            .quantity(orderItemRequest.getQuantity())
                            .unitPrice(product.getPrice())
                            .build();})
                .forEach(order::addOrderItem);

        return orderRepository.save(order);
    }

    private OrderResponse mapToOrderResponse(Order order){
      return OrderResponse.builder()
                .orderId(order.getId())
                .productName(order.getUser().getName())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .build();

    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        String email = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        validateStockAvailability(request);

        BigDecimal totalAmount = calculateTotal(request);

        Order order = savePendingOrder(request, user, totalAmount);

        return mapToOrderResponse(order);

    }

    private BigDecimal calculateTotal(OrderRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with ID: " + request.getProductId()));

        BigDecimal price = product.getPrice();
        BigDecimal quantity = BigDecimal.valueOf(request.getQuantity());

        return price.multiply(quantity).setScale(2, RoundingMode.HALF_UP);
    }
    private void validateStockAvailability(OrderRequest request) {
        for (OrderItemRequest item : request.getItemList()) {
            int currentStock = inventoryMovementRepository.getStock(item.getProductId());
            if (currentStock < item.getQuantity()) {
                throw new InsufficientStockException("Product " + item.getProductId() + " is out of stock");
            }
        }
    }



    @Override
    public void finalizeTransaction(String reference) {
        PaymentStatus status = paymentGatewayService.checkPaymentStatus(reference);

        if (status == PaymentStatus.SUCCESS) {
            Payment payment = paymentRepository.findByReference(reference)
                    .orElseThrow(() -> new PaymentNotFoundException("Payment reference not found: " + reference));

            Order order = payment.getOrder();
            order.setStatus(Status.PAID);
            payment.setStatus(PaymentStatus.SUCCESS);

            order.getOrderItems()
                    .forEach(item -> inventoryMovementService.deductStock(
                            item.getProduct().getId(),
                            item.getQuantity(),
                            Reason.SALE));

            orderRepository.save(order);
            paymentRepository.save(payment);
        }
    }

    @Override
    public Page<OrderResponse> getOrders(String search, Pageable pageable) {
        Specification<Order> spec = (root, query, cb) -> {
            if (search == null || search.isEmpty()) {
                return null;
            }

            String searchPattern = "%" + search.toLowerCase() + "%";

            Join<Order, OrderItem> items = root.join("orderItems", JoinType.LEFT);
            Join<OrderItem, Product> product = items.join("product",JoinType.LEFT);
            query.distinct(true);

            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.like(cb.lower(product.get("name")), searchPattern));
            predicates.add(cb.like(cb.lower(product.get("category").get("name")),searchPattern));

            if (search.matches("\\d+")) {
                predicates.add(cb.equal(root.get("id"), Long.parseLong(search)));
            }

            return cb.or(predicates.toArray(new Predicate[0]));
        };
        return productRepository.findAll(spec, pageable)
                .map(this::mapToOrderResponse);
    }

}
