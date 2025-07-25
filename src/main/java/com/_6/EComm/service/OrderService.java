package com._6.EComm.service;

import com._6.EComm.dto.OrderRequest;
import com._6.EComm.dto.OrderResponse;
import com._6.EComm.dto.OrderStatusUpdateRequest;
import java.util.List;

public interface OrderService {
    OrderResponse placeOrder(OrderRequest request);
    List<OrderResponse> getUserOrders(Long userId);
    List<OrderResponse> getAllOrders();
    OrderResponse updateOrderStatus(Long orderId, OrderStatusUpdateRequest request);
} 