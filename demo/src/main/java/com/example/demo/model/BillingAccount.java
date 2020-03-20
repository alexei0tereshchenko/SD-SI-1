package com.example.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class BillingAccount extends Base {
   private Customer customer;
   private BigDecimal balance;
   private Status status;
}
