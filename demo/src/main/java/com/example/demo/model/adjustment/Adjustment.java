package com.example.demo.model.adjustment;
import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

@Data
@EqualsAndHashCode()
@ObjectType(objectTypeId = "6")
public class Adjustment extends Base {
    public static final int ADJUSTMENT_LIMIT = 5;
    @Attribute(attrId = "25")
    public AdjustmentStatus adjustmentStatus;
    @Attribute(attrId = "26")
    public Date approvedDate;
    @Attribute(attrId = "27")
    public String rejectedReason;
}
