package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Customer extends Base{
    private String firstName;
    private String lastName;
    private Status status;
}
