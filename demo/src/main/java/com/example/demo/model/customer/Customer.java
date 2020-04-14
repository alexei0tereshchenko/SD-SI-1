package com.example.demo.model.customer;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.model.Base;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ObjectType(objectTypeId = "2")
public class Customer extends Base {
    @Attribute(attrId = "1")
    private String firstName;

    @Attribute(attrId = "2")
    private String lastName;

    @Attribute(attrId = "6")
    private Status status;
}
