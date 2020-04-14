package com.example.demo.dto.payment;

import com.example.demo.model.payment.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentDto {
    private Long parentId; //id of the connected billing account
    private String createdBy;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
}
