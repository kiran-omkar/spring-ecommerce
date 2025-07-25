package com._6.EComm.service;

import com._6.EComm.dto.PaymentRequest;
import com._6.EComm.dto.PaymentResponse;
import com._6.EComm.dto.RefundRequest;

public interface PaymentService {
    PaymentResponse processPayment(PaymentRequest request);
    PaymentResponse refundPayment(RefundRequest request);
    PaymentResponse getPaymentByOrder(Long orderId);
} 