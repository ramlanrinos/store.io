package com.storeio.inventoryservice.service;

import com.storeio.inventoryservice.dto.*;
import com.storeio.inventoryservice.entity.ChangeType;
import com.storeio.inventoryservice.entity.Inventory;
import com.storeio.inventoryservice.entity.InventoryLog;
import com.storeio.inventoryservice.exception.BadRequestException;
import com.storeio.inventoryservice.exception.InsufficientStockException;
import com.storeio.inventoryservice.exception.ResourceNotFoundException;
import com.storeio.inventoryservice.repository.InventoryLogRepository;
import com.storeio.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryLogRepository inventoryLogRepository;

    @Transactional(readOnly = true)
    public InventoryResponse getInventoryByProductId(Long productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory balance not found for product ID: " + productId));
        return mapToInventoryResponse(inventory);
    }

    @Transactional
    public void reserveStock(ReserveStockRequest request) {
        for (StockItemDto item : request.getItems()) {
            Inventory inventory = inventoryRepository.findByProductId(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product ID: " + item.getProductId()));

            if (inventory.getAvailableQuantity() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient available stock for product ID " + item.getProductId()
                        + ". Requested: " + item.getQuantity() + ", Available: " + inventory.getAvailableQuantity());
            }

            inventory.setAvailableQuantity(inventory.getAvailableQuantity() - item.getQuantity());
            inventory.setReservedQuantity(inventory.getReservedQuantity() + item.getQuantity());
            inventoryRepository.save(inventory);

            logInventoryChange(item.getProductId(), ChangeType.RESERVE, item.getQuantity(),
                    "Reserved for order: " + (request.getOrderNumber() != null ? request.getOrderNumber() : "N/A"));
        }
    }

    @Transactional
    public void releaseStock(ReleaseStockRequest request) {
        for (StockItemDto item : request.getItems()) {
            Inventory inventory = inventoryRepository.findByProductId(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product ID: " + item.getProductId()));

            int releaseQty = Math.min(inventory.getReservedQuantity(), item.getQuantity());
            inventory.setReservedQuantity(inventory.getReservedQuantity() - releaseQty);
            inventory.setAvailableQuantity(inventory.getAvailableQuantity() + releaseQty);
            inventoryRepository.save(inventory);

            logInventoryChange(item.getProductId(), ChangeType.RELEASE, releaseQty,
                    "Released for order: " + (request.getOrderNumber() != null ? request.getOrderNumber() : "N/A"));
        }
    }

    @Transactional
    public void deductStock(DeductStockRequest request) {
        for (StockItemDto item : request.getItems()) {
            Inventory inventory = inventoryRepository.findByProductId(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Inventory not found for product ID: " + item.getProductId()));

            int deductQty = Math.min(inventory.getReservedQuantity(), item.getQuantity());
            inventory.setReservedQuantity(inventory.getReservedQuantity() - deductQty);
            inventoryRepository.save(inventory);

            logInventoryChange(item.getProductId(), ChangeType.DEDUCT, deductQty,
                    "Deducted for paid order: " + (request.getOrderNumber() != null ? request.getOrderNumber() : "N/A"));
        }
    }

    @Transactional
    public InventoryResponse restock(RestockRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(request.getProductId())
                .orElseGet(() -> Inventory.builder()
                        .productId(request.getProductId())
                        .sku(request.getSku())
                        .availableQuantity(0)
                        .reservedQuantity(0)
                        .build());

        inventory.setAvailableQuantity(inventory.getAvailableQuantity() + request.getQuantity());
        Inventory savedInventory = inventoryRepository.save(inventory);

        logInventoryChange(request.getProductId(), ChangeType.RESTOCK, request.getQuantity(),
                request.getReason() != null ? request.getReason() : "Manual Admin Restock");

        return mapToInventoryResponse(savedInventory);
    }

    private void logInventoryChange(Long productId, ChangeType changeType, int quantity, String reason) {
        InventoryLog log = InventoryLog.builder()
                .productId(productId)
                .changeType(changeType)
                .quantityChanged(quantity)
                .reason(reason)
                .build();
        inventoryLogRepository.save(log);
    }

    private InventoryResponse mapToInventoryResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .sku(inventory.getSku())
                .availableQuantity(inventory.getAvailableQuantity())
                .reservedQuantity(inventory.getReservedQuantity())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }
}
