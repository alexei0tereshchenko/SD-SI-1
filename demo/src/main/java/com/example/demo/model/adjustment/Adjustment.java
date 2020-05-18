package com.example.demo.model.adjustment;
import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@ObjectType(objectTypeId = "4")
public class Adjustment extends Base {
    @Attribute(attrId = "14")
    public AdjustmentStatus adjustmentStatus;
    @Attribute(attrId = "15")
    public Date approvedDate;
    @Attribute(attrId = "16")
    public String rejectedReason;

}
