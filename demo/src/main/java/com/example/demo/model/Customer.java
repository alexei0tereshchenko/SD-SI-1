package com.example.demo.model;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ObjectType(objectTypeId = "2")
public class Customer extends Base {
    @Attribute(attrId = "1")
    public String firstName;

    @Attribute(attrId = "2")
    public String lastName;

    @Attribute(attrId = "6")
    public Status status;
}
