package com.example.demo.dto.bill;

import com.example.demo.model.Bill.BillStatus;
import com.example.demo.model.Bill.BillStyle;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateBillDto {
    private Long parentId;
    private BigDecimal amount;
    public BillStatus billStatus;
    public BillStyle billStyle;
    public Long billNumber;
}
