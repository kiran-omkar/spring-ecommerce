package com._6.EComm.dto;

import com._6.EComm.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateRequest(
    @NotNull OrderStatus status
) {} 