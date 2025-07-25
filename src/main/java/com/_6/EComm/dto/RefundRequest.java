package com._6.EComm.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record RefundRequest(
    @NotNull Long orderId,
    @NotNull BigDecimal amount
) {} 