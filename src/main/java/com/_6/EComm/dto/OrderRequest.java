package com._6.EComm.dto;

import jakarta.validation.constraints.NotNull;

public record OrderRequest(
    @NotNull Long userId
) {} 