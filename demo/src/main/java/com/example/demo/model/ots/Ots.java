package com.example.demo.model.ots;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@ObjectType(objectTypeId = "5")
public class Ots extends Base {
    static public final int OTS_LIMIT_FOR_BILL_ACCOUNT = 5;

    @Attribute(attrId = "14")
    private String description;

    @Attribute(attrId = "15")
    private Date createdWhen;

    @Attribute(attrId = "3")
    private BigDecimal amount;

    @Attribute(attrId = "21")
    private OtsStatus otsStatus;

    @Attribute(attrId = "22")
    private String terminationReason;

    @Attribute(attrId = "23")
    private boolean instalments;

    @Attribute(attrId = "18")
    private Date cancellationDate;
}
