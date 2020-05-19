package com.example.demo.dto.bill;

import com.example.demo.model.Bill.BillStatus;
import com.example.demo.model.Bill.BillStyle;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateBillDto {
    private Long parentId;
    private BigDecimal amount;
    private BillStatus billStatus;
    private BillStyle billStyle;
    private Long billNumber;
}
