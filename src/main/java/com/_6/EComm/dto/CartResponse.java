package com._6.EComm.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
    Long userId,
    List<CartItemResponse> items,
    BigDecimal total
) {} 