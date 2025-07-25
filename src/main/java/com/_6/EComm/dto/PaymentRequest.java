package com._6.EComm.dto;

import com._6.EComm.entity.PaymentStatus;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record PaymentRequest(
    @NotNull Long orderId,
    @NotNull BigDecimal amount,
    @NotNull PaymentStatus simulateStatus,
    String failureReason
) {} 