package com._6.EComm.dto;

import com._6.EComm.entity.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
    Long paymentId,
    Long orderId,
    PaymentStatus status,
    BigDecimal amount,
    String transactionId,
    String failureReason,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {} 