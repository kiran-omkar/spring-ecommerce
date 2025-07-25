package com._6.EComm.dto;

import com._6.EComm.entity.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.math.BigDecimal;

public record OrderResponse(
    Long orderId,
    Long userId,
    List<OrderItemResponse> items,
    OrderStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    BigDecimal total
) {} 