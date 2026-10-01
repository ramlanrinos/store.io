package com.storeio.inventoryservice.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReserveStockRequest {

    private String orderNumber;

    @NotEmpty(message = "Items list cannot be empty")
    @Valid
    private List<StockItemDto> items;
}
