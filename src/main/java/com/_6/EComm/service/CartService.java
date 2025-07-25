package com._6.EComm.service;

import com._6.EComm.dto.CartItemRequest;
import com._6.EComm.dto.CartResponse;

public interface CartService {
    CartResponse addToCart(CartItemRequest request);
    CartResponse getCart(Long userId);
    CartResponse updateCartItem(CartItemRequest request);
    CartResponse removeCartItem(Long userId, Long productId);
} 