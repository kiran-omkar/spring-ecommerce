package com._6.EComm.controller;

import com._6.EComm.dto.PaymentRequest;
import com._6.EComm.dto.PaymentResponse;
import com._6.EComm.dto.RefundRequest;
import com._6.EComm.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<PaymentResponse> processPayment(@Valid @RequestBody PaymentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.processPayment(request));
    }

    @PostMapping("/refund")
    public PaymentResponse refundPayment(@Valid @RequestBody RefundRequest request) {
        return paymentService.refundPayment(request);
    }

    @GetMapping("/order/{orderId}")
    public PaymentResponse getPaymentByOrder(@PathVariable Long orderId) {
        return paymentService.getPaymentByOrder(orderId);
    }
} 