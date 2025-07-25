package com._6.EComm.controller;

import com._6.EComm.dto.CartItemRequest;
import com._6.EComm.dto.CartResponse;
import com._6.EComm.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponse> addToCart(@Valid @RequestBody CartItemRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cartService.addToCart(request));
    }

    @GetMapping("/{userId}")
    public CartResponse getCart(@PathVariable Long userId) {
        return cartService.getCart(userId);
    }

    @PutMapping("/update")
    public CartResponse updateCartItem(@Valid @RequestBody CartItemRequest request) {
        return cartService.updateCartItem(request);
    }

    @DeleteMapping("/remove")
    public CartResponse removeCartItem(@RequestParam Long userId, @RequestParam Long productId) {
        return cartService.removeCartItem(userId, productId);
    }
} 