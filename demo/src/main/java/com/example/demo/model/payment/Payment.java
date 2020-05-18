package com.example.demo.model.payment;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ObjectType(objectTypeId = "4")
public class Payment extends Base {
    @Attribute(attrId = "14")
    private String description;

    @Attribute(attrId = "15")
    private Date createdWhen;

    @Attribute(attrId = "3")
    private BigDecimal amount;

    @Attribute(attrId = "6")
    private PaymentStatus status;

    @Attribute(attrId = "16")
    private PaymentMethod paymentMethod;

    @Attribute(attrId = "17")
    private String createdBy;

    @Attribute(attrId = "18")
    private Date cancellationDate;
}
