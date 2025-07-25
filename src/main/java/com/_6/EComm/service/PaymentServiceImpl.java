package com._6.EComm.service;

import com._6.EComm.dto.PaymentRequest;
import com._6.EComm.dto.PaymentResponse;
import com._6.EComm.dto.RefundRequest;
import com._6.EComm.entity.Order;
import com._6.EComm.entity.Payment;
import com._6.EComm.entity.PaymentStatus;
import com._6.EComm.repository.OrderRepository;
import com._6.EComm.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, OrderRepository orderRepository) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public PaymentResponse processPayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.orderId()).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        Optional<Payment> existing = paymentRepository.findByOrderId(order.getId());
        if (existing.isPresent()) {
            throw new IllegalStateException("Payment already exists for this order");
        }
        String transactionId = UUID.randomUUID().toString();
        Payment payment = new Payment(
            order,
            request.simulateStatus(),
            request.amount(),
            transactionId,
            request.simulateStatus() == PaymentStatus.FAILED ? request.failureReason() : null
        );
        payment = paymentRepository.save(payment);
        return toPaymentResponse(payment);
    }

    @Override
    public PaymentResponse refundPayment(RefundRequest request) {
        Payment payment = paymentRepository.findByOrderId(request.orderId()).orElseThrow(() -> new IllegalArgumentException("Payment not found for order"));
        if (payment.getStatus() == PaymentStatus.REFUNDED) {
            throw new IllegalStateException("Payment already refunded");
        }
        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);
        return toPaymentResponse(payment);
    }

    @Override
    public PaymentResponse getPaymentByOrder(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("Payment not found for order"));
        return toPaymentResponse(payment);
    }

    private PaymentResponse toPaymentResponse(Payment payment) {
        return new PaymentResponse(
            payment.getId(),
            payment.getOrder().getId(),
            payment.getStatus(),
            payment.getAmount(),
            payment.getTransactionId(),
            payment.getFailureReason(),
            payment.getCreatedAt(),
            payment.getUpdatedAt()
        );
    }
} 