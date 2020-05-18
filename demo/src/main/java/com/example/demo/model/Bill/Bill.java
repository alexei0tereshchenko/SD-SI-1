package com.example.demo.model.Bill;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ObjectType(objectTypeId = "7")
public class Bill extends Base {
    static public final int BILL_LIMIT_FOR_BILL_ACCOUNT = 5;

    @Attribute(attrId = "28")
    public BillStatus BillStatus;

    @Attribute(attrId = "29")
    public BillStyle BillStyle;

    @Attribute(attrId = "30")
    public Long BillNumber;

    @Attribute(attrId = "31")
    public String Specification;

    @Attribute(attrId = "3")
    private BigDecimal amount;
}