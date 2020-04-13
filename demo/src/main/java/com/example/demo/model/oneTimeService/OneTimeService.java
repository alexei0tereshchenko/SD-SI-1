package com.example.demo.model.oneTimeService;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@ObjectType(objectTypeId = "5")
public class OneTimeService extends Base {

    @Attribute(attrId = "21")
    private OneTimeServiceStatus otsStatus;

    @Attribute(attrId = "22")
    private String terminationReason;

    @Attribute(attrId = "23")
    private boolean instalments;

    @Attribute(attrId = "24")
    private Date cancellationDate;
}
