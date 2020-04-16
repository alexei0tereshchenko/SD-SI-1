package com.example.demo.model.Bill;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ObjectType(objectTypeId = "7")
public class Bill extends Base {
    @Attribute(attrId = "28")
    public BillStatus BillStatus;
    @Attribute(attrId = "29")
    public BillStyle BillStyle;
    @Attribute(attrId = "30")
    public long BillNumber;
    @Attribute (attrId = "31")
    public String Specification;
}