package com._6.EComm.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductRequest(
  @NotBlank String name,
  @NotNull @PositiveOrZero BigDecimal price,
  String description,
  @NotNull Long categoryId
) {}    
