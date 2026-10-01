package com.storeio.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InventoryResponse {
    private Long id;
    private Long productId;
    private String sku;
    private Integer availableQuantity;
    private Integer reservedQuantity;
    private LocalDateTime updatedAt;
}
