package com.storeio.inventoryservice.controller;

import com.storeio.inventoryservice.dto.*;
import com.storeio.inventoryservice.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventoryByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(inventoryService.getInventoryByProductId(productId));
    }

    @PostMapping("/reserve")
    public ResponseEntity<Void> reserveStock(@Valid @RequestBody ReserveStockRequest request) {
        inventoryService.reserveStock(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/release")
    public ResponseEntity<Void> releaseStock(@Valid @RequestBody ReleaseStockRequest request) {
        inventoryService.releaseStock(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deduct")
    public ResponseEntity<Void> deductStock(@Valid @RequestBody DeductStockRequest request) {
        inventoryService.deductStock(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/restock")
    public ResponseEntity<InventoryResponse> restock(@Valid @RequestBody RestockRequest request) {
        InventoryResponse response = inventoryService.restock(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
