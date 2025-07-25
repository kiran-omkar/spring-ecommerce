package com._6.EComm.service;

import com._6.EComm.dto.*;
import com._6.EComm.entity.*;
import com._6.EComm.repository.*;
import com._6.EComm.service.InventoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final InventoryService inventoryService;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CartRepository cartRepository, ProductRepository productRepository, UserRepository userRepository, InventoryService inventoryService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public OrderResponse placeOrder(OrderRequest request) {
        User user = userRepository.findById(request.userId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Cart cart = cartRepository.findByUserId(user.getId()).orElseThrow(() -> new IllegalArgumentException("Cart is empty"));
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        // Stock check for all items before decreasing
        for (CartItem cartItem : cart.getItems()) {
            int available = inventoryService.getStock(cartItem.getProduct().getId());
            if (available < cartItem.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product: " + cartItem.getProduct().getName());
            }
        }
        // Decrease stock for all items
        for (CartItem cartItem : cart.getItems()) {
            inventoryService.decreaseStock(cartItem.getProduct().getId(), cartItem.getQuantity());
        }
        Order order = new Order(user, OrderStatus.PLACED);
        order = orderRepository.save(order);
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem(order, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getProduct().getPrice());
            order.getItems().add(orderItem);
            orderItemRepository.save(orderItem);
        }
        orderRepository.save(order);
        // Clear cart
        cart.getItems().clear();
        cartRepository.save(cart);
        return toOrderResponse(order);
    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {
        List<Order> orders = orderRepository.findByUserId(userId);
        return orders.stream().map(this::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(this::toOrderResponse).collect(Collectors.toList());
    }

    @Override
    public OrderResponse updateOrderStatus(Long orderId, OrderStatusUpdateRequest request) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Order not found"));
        order.setStatus(request.status());
        orderRepository.save(order);
        return toOrderResponse(order);
    }

    private OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> items = order.getItems().stream().map(item ->
            new OrderItemResponse(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice()
            )
        ).collect(Collectors.toList());
        BigDecimal total = items.stream().map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
        return new OrderResponse(
            order.getId(),
            order.getUser().getId(),
            items,
            order.getStatus(),
            order.getCreatedAt(),
            order.getUpdatedAt(),
            total
        );
    }
} 