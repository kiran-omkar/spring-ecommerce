package com._6.EComm.service;

import com._6.EComm.dto.CartItemRequest;
import com._6.EComm.dto.CartItemResponse;
import com._6.EComm.dto.CartResponse;
import com._6.EComm.entity.Cart;
import com._6.EComm.entity.CartItem;
import com._6.EComm.entity.Product;
import com._6.EComm.entity.User;
import com._6.EComm.repository.CartItemRepository;
import com._6.EComm.repository.CartRepository;
import com._6.EComm.repository.ProductRepository;
import com._6.EComm.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartServiceImpl implements CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CartResponse addToCart(CartItemRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Product product = productRepository.findById(request.productId()).orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseGet(() -> {
            Cart newCart = new Cart(user);
            return cartRepository.save(newCart);
        });
        Optional<CartItem> existingItem = cart.getItems().stream().filter(item -> java.util.Objects.equals(item.getProduct().getId(), product.getId())).findFirst();
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + request.quantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = new CartItem(cart, product, request.quantity());
            cart.getItems().add(newItem);
            cartItemRepository.save(newItem);
        }
        cartRepository.save(cart);
        return toCartResponse(cart);
    }

    @Override
    public CartResponse getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
            return cartRepository.save(new Cart(user));
        });
        return toCartResponse(cart);
    }

    @Override
    public CartResponse updateCartItem(CartItemRequest request) {
        Cart cart = cartRepository.findByUserId(request.userId()).orElseThrow(() -> new IllegalArgumentException("Cart not found for user"));
        CartItem item = cart.getItems().stream().filter(i -> java.util.Objects.equals(i.getProduct().getId(), request.productId())).findFirst().orElseThrow(() -> new IllegalArgumentException("Product not in cart"));
        item.setQuantity(request.quantity());
        cartItemRepository.save(item);
        cartRepository.save(cart);
        return toCartResponse(cart);
    }

    @Override
    public CartResponse removeCartItem(Long userId, Long productId) {
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Cart not found for user"));
        CartItem item = cart.getItems().stream().filter(i -> java.util.Objects.equals(i.getProduct().getId(), productId)).findFirst().orElseThrow(() -> new IllegalArgumentException("Product not in cart"));
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        cartRepository.save(cart);
        return toCartResponse(cart);
    }

    private CartResponse toCartResponse(Cart cart) {
        List<CartItemResponse> items = cart.getItems().stream().map(item ->
            new CartItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getProduct().getPrice()
            )
        ).collect(Collectors.toList());
        BigDecimal total = items.stream().map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartResponse(cart.getUser().getId(), items, total);
    }
} 