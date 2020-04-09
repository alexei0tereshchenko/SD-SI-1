package com.example.demo.dto.payment;

import com.example.demo.model.Payment.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentDto {
    private String createdBy;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
}
