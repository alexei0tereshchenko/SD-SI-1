package com.example.demo.adjustment;
import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Calendar;

@Data
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
