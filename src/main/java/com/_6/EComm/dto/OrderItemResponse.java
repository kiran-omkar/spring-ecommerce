package com._6.EComm.dto;

import java.math.BigDecimal;

public record OrderItemResponse(
    Long productId,
    String productName,
    int quantity,
    BigDecimal price
) {} 