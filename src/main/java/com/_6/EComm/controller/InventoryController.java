package com._6.EComm.controller;

import com._6.EComm.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping("/product/{id}/stock")
    public ResponseEntity<Void> updateStock(@PathVariable Long id, @RequestParam int newStock) {
        inventoryService.updateStock(id, newStock);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product/{id}/stock")
    public int getStock(@PathVariable Long id) {
        return inventoryService.getStock(id);
    }
} 