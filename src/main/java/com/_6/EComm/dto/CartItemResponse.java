package com._6.EComm.dto;

import java.math.BigDecimal;

public record CartItemResponse(
    Long productId,
    String productName,
    int quantity,
    BigDecimal price
) {} 