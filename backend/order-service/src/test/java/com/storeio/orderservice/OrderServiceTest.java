package com.storeio.orderservice;

import com.storeio.orderservice.dto.*;
import com.storeio.orderservice.entity.Order;
import com.storeio.orderservice.entity.OrderItem;
import com.storeio.orderservice.entity.OrderStatus;
import com.storeio.orderservice.repository.OrderRepository;
import com.storeio.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private Order sampleOrder;

    @BeforeEach
    void setUp() {
        sampleOrder = Order.builder()
                .id(1L)
                .orderNumber("ORD-20261002-A1B2C3")
                .userId(101L)
                .totalAmount(new BigDecimal("599.98"))
                .status(OrderStatus.PENDING)
                .shippingAddress("123 Main St, New York, NY 10001")
                .items(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        OrderItem item = OrderItem.builder()
                .id(10L)
                .order(sampleOrder)
                .productId(501L)
                .productName("Wireless Noise-Canceling Headphones")
                .priceSnapshot(new BigDecimal("299.99"))
                .quantity(2)
                .build();

        sampleOrder.getItems().add(item);
    }

    @Test
    void createOrder_Success() {
        OrderItemRequest itemReq = OrderItemRequest.builder()
                .productId(501L)
                .productName("Wireless Noise-Canceling Headphones")
                .priceSnapshot(new BigDecimal("299.99"))
                .quantity(2)
                .build();

        CreateOrderRequest request = CreateOrderRequest.builder()
                .userId(101L)
                .shippingAddress("123 Main St, New York, NY 10001")
                .items(Collections.singletonList(itemReq))
                .build();

        when(orderRepository.save(any(Order.class))).thenReturn(sampleOrder);

        OrderResponse response = orderService.createOrder(request);

        assertNotNull(response);
        assertEquals(OrderStatus.PENDING, response.getStatus());
        assertEquals(new BigDecimal("599.98"), response.getTotalAmount());
        assertEquals(1, response.getItems().size());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void getOrderById_Success() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));

        OrderResponse response = orderService.getOrderById(1L);

        assertNotNull(response);
        assertEquals("ORD-20261002-A1B2C3", response.getOrderNumber());
        assertEquals(101L, response.getUserId());
    }

    @Test
    void getOrdersByUserId_Success() {
        when(orderRepository.findByUserIdOrderByCreatedAtDesc(101L))
                .thenReturn(Collections.singletonList(sampleOrder));

        List<OrderResponse> responses = orderService.getOrdersByUserId(101L);

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(101L, responses.get(0).getUserId());
    }

    @Test
    void updateOrderStatus_Success() {
        UpdateOrderStatusRequest request = UpdateOrderStatusRequest.builder()
                .status(OrderStatus.PAID)
                .build();

        when(orderRepository.findById(1L)).thenReturn(Optional.of(sampleOrder));
        when(orderRepository.save(any(Order.class))).thenReturn(sampleOrder);

        OrderResponse response = orderService.updateOrderStatus(1L, request);

        assertNotNull(response);
        assertEquals(OrderStatus.PAID, sampleOrder.getStatus());
        verify(orderRepository, times(1)).save(sampleOrder);
    }
}
