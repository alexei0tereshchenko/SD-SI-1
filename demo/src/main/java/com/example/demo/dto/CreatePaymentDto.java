package com.example.demo.dto;

import com.example.demo.model.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreatePaymentDto {
    private String createdBy;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
}
