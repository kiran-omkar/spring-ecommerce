package com._6.EComm.dto;

import java.math.BigDecimal;

public record ProductResponse(
  Long id,
  String name,
  BigDecimal price,
  String description,
  CategoryResponse category
) {}
