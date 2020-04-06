package com.example.demo.model;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@ObjectType(objectTypeId = "6")
public class Payment extends Base{
    @Attribute(attrId = "14")
    private String description;

    @Attribute(attrId = "15")
    private LocalDateTime createdWhen;

    @Attribute(attrId = "16")
    private BigDecimal amount;

    @Attribute(attrId = "17")
    private PaymentStatus status;

    @Attribute(attrId = "18")
    private PaymentMethod paymentMethod;

    @Attribute(attrId = "19")
    private String createdBy;

    @Attribute(attrId = "20")
    private LocalDateTime cancellationDate;
}
