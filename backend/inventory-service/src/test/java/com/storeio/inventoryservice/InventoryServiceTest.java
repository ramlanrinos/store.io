package com.storeio.inventoryservice;

import com.storeio.inventoryservice.dto.*;
import com.storeio.inventoryservice.entity.Inventory;
import com.storeio.inventoryservice.entity.InventoryLog;
import com.storeio.inventoryservice.exception.InsufficientStockException;
import com.storeio.inventoryservice.repository.InventoryLogRepository;
import com.storeio.inventoryservice.repository.InventoryRepository;
import com.storeio.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private InventoryLogRepository inventoryLogRepository;

    @InjectMocks
    private InventoryService inventoryService;

    private Inventory sampleInventory;

    @BeforeEach
    void setUp() {
        sampleInventory = Inventory.builder()
                .id(1L)
                .productId(101L)
                .sku("AUD-HEAD-001")
                .availableQuantity(50)
                .reservedQuantity(0)
                .version(1L)
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getInventoryByProductId_Success() {
        when(inventoryRepository.findByProductId(101L)).thenReturn(Optional.of(sampleInventory));

        InventoryResponse response = inventoryService.getInventoryByProductId(101L);

        assertNotNull(response);
        assertEquals(101L, response.getProductId());
        assertEquals(50, response.getAvailableQuantity());
        assertEquals(0, response.getReservedQuantity());
    }

    @Test
    void reserveStock_Success() {
        ReserveStockRequest request = ReserveStockRequest.builder()
                .orderNumber("ORD-1001")
                .items(Collections.singletonList(StockItemDto.builder().productId(101L).quantity(5).build()))
                .build();

        when(inventoryRepository.findByProductId(101L)).thenReturn(Optional.of(sampleInventory));

        inventoryService.reserveStock(request);

        assertEquals(45, sampleInventory.getAvailableQuantity());
        assertEquals(5, sampleInventory.getReservedQuantity());
        verify(inventoryRepository, times(1)).save(sampleInventory);
        verify(inventoryLogRepository, times(1)).save(any(InventoryLog.class));
    }

    @Test
    void reserveStock_InsufficientStock_ThrowsException() {
        ReserveStockRequest request = ReserveStockRequest.builder()
                .orderNumber("ORD-1001")
                .items(Collections.singletonList(StockItemDto.builder().productId(101L).quantity(100).build()))
                .build();

        when(inventoryRepository.findByProductId(101L)).thenReturn(Optional.of(sampleInventory));

        assertThrows(InsufficientStockException.class, () -> inventoryService.reserveStock(request));
    }

    @Test
    void releaseStock_Success() {
        sampleInventory.setAvailableQuantity(40);
        sampleInventory.setReservedQuantity(10);

        ReleaseStockRequest request = ReleaseStockRequest.builder()
                .orderNumber("ORD-1001")
                .items(Collections.singletonList(StockItemDto.builder().productId(101L).quantity(5).build()))
                .build();

        when(inventoryRepository.findByProductId(101L)).thenReturn(Optional.of(sampleInventory));

        inventoryService.releaseStock(request);

        assertEquals(45, sampleInventory.getAvailableQuantity());
        assertEquals(5, sampleInventory.getReservedQuantity());
        verify(inventoryRepository, times(1)).save(sampleInventory);
    }

    @Test
    void restock_Success() {
        RestockRequest request = RestockRequest.builder()
                .productId(101L)
                .sku("AUD-HEAD-001")
                .quantity(20)
                .reason("Weekly Restock")
                .build();

        when(inventoryRepository.findByProductId(101L)).thenReturn(Optional.of(sampleInventory));
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(sampleInventory);

        InventoryResponse response = inventoryService.restock(request);

        assertNotNull(response);
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }
}
