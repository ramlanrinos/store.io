package com.storeio.paymentservice;

import com.storeio.paymentservice.dto.PaymentResponse;
import com.storeio.paymentservice.dto.ProcessPaymentRequest;
import com.storeio.paymentservice.dto.RefundRequest;
import com.storeio.paymentservice.entity.Payment;
import com.storeio.paymentservice.entity.PaymentLog;
import com.storeio.paymentservice.entity.PaymentStatus;
import com.storeio.paymentservice.repository.PaymentLogRepository;
import com.storeio.paymentservice.repository.PaymentRepository;
import com.storeio.paymentservice.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentLogRepository paymentLogRepository;

    @InjectMocks
    private PaymentService paymentService;

    private Payment samplePayment;

    @BeforeEach
    void setUp() {
        samplePayment = Payment.builder()
                .id(1L)
                .transactionId("TXN-ABC123DEF456")
                .orderId(1001L)
                .amount(new BigDecimal("599.98"))
                .paymentMethod("CREDIT_CARD")
                .status(PaymentStatus.SUCCESS)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Test
    void processPayment_Success() {
        ProcessPaymentRequest request = ProcessPaymentRequest.builder()
                .orderId(1001L)
                .amount(new BigDecimal("599.98"))
                .paymentMethod("CREDIT_CARD")
                .build();

        when(paymentRepository.existsByOrderId(1001L)).thenReturn(false);
        when(paymentRepository.save(any(Payment.class))).thenReturn(samplePayment);

        PaymentResponse response = paymentService.processPayment(request);

        assertNotNull(response);
        assertEquals(PaymentStatus.SUCCESS, response.getStatus());
        assertEquals(1001L, response.getOrderId());
        assertEquals(new BigDecimal("599.98"), response.getAmount());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(paymentLogRepository, times(1)).save(any(PaymentLog.class));
    }

    @Test
    void getPaymentById_Success() {
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(samplePayment));

        PaymentResponse response = paymentService.getPaymentById(1L);

        assertNotNull(response);
        assertEquals("TXN-ABC123DEF456", response.getTransactionId());
        assertEquals(1001L, response.getOrderId());
    }

    @Test
    void refundPayment_Success() {
        RefundRequest request = RefundRequest.builder()
                .reason("Customer requested order cancellation")
                .build();

        when(paymentRepository.findById(1L)).thenReturn(Optional.of(samplePayment));
        when(paymentRepository.save(any(Payment.class))).thenReturn(samplePayment);

        PaymentResponse response = paymentService.refundPayment(1L, request);

        assertNotNull(response);
        assertEquals(PaymentStatus.REFUNDED, samplePayment.getStatus());
        verify(paymentRepository, times(1)).save(samplePayment);
        verify(paymentLogRepository, times(1)).save(any(PaymentLog.class));
    }
}
