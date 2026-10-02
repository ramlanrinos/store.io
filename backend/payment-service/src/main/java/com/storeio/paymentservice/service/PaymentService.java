package com.storeio.paymentservice.service;

import com.storeio.paymentservice.dto.PaymentResponse;
import com.storeio.paymentservice.dto.ProcessPaymentRequest;
import com.storeio.paymentservice.dto.RefundRequest;
import com.storeio.paymentservice.entity.Payment;
import com.storeio.paymentservice.entity.PaymentLog;
import com.storeio.paymentservice.entity.PaymentStatus;
import com.storeio.paymentservice.exception.BadRequestException;
import com.storeio.paymentservice.exception.ResourceNotFoundException;
import com.storeio.paymentservice.repository.PaymentLogRepository;
import com.storeio.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentLogRepository paymentLogRepository;

    @Transactional
    public PaymentResponse processPayment(ProcessPaymentRequest request) {
        if (paymentRepository.existsByOrderId(request.getOrderId())) {
            throw new BadRequestException("Payment transaction already exists for order ID: " + request.getOrderId());
        }

        String transactionId = "TXN-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();

        Payment payment = Payment.builder()
                .transactionId(transactionId)
                .orderId(request.getOrderId())
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .status(PaymentStatus.SUCCESS)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        logTransaction(savedPayment,
                "Request: orderId=" + request.getOrderId() + ", amount=" + request.getAmount() + ", method=" + request.getPaymentMethod(),
                "Response: status=SUCCESS, txnId=" + transactionId);

        return mapToPaymentResponse(savedPayment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment transaction not found with id: " + id));
        return mapToPaymentResponse(payment);
    }

    @Transactional(readOnly = true)
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment transaction not found for order ID: " + orderId));
        return mapToPaymentResponse(payment);
    }

    @Transactional
    public PaymentResponse refundPayment(Long id, RefundRequest request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment transaction not found with id: " + id));

        if (payment.getStatus() == PaymentStatus.REFUNDED) {
            throw new BadRequestException("Payment transaction is already refunded");
        }

        payment.setStatus(PaymentStatus.REFUNDED);
        Payment updatedPayment = paymentRepository.save(payment);

        logTransaction(updatedPayment,
                "Refund Request: reason=" + request.getReason(),
                "Refund Response: status=REFUNDED, amount=" + payment.getAmount());

        return mapToPaymentResponse(updatedPayment);
    }

    private void logTransaction(Payment payment, String requestPayload, String responsePayload) {
        PaymentLog log = PaymentLog.builder()
                .payment(payment)
                .requestPayload(requestPayload)
                .responsePayload(responsePayload)
                .build();
        paymentLogRepository.save(log);
    }

    private PaymentResponse mapToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .transactionId(payment.getTransactionId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .paymentMethod(payment.getPaymentMethod())
                .status(payment.getStatus())
                .createdAt(payment.getCreatedAt())
                .build();
    }
}
