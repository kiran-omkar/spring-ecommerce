package com._6.EComm.service;

public interface InventoryService {
    void decreaseStock(Long productId, int quantity);
    void updateStock(Long productId, int newStock);
    int getStock(Long productId);
} 