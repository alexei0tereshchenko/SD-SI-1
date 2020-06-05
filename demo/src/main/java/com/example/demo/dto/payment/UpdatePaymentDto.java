package com.example.demo.dto.payment;

import com.example.demo.model.payment.PaymentMethod;
import com.example.demo.model.payment.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdatePaymentDto {
    private Long parentId; //id of the connected billing account
    private String description;
    private Date createdWhen;
    private BigDecimal amount;
    private PaymentStatus status;
    private PaymentMethod paymentMethod;
    private String createdBy;
    private Date cancellationDate;
}
