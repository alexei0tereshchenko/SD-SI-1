package com.example.demo.model;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@EqualsAndHashCode()
@ObjectType(objectTypeId = "4")
public class Adjustment {
    @Attribute(attrId = "14")
    public AdjustmentStatus adjustmentStatus;
    @Attribute(attrId = "15")
    public Calendar approvedDate;
    @Attribute(attrId = "16")
    public String rejectedReason;

}
