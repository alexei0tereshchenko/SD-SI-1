package com.example.demo.dto.Bill;

import com.example.demo.model.Bill.BillStatus;
import com.example.demo.model.Bill.BillStyle;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UpdateBillDto {
    private String description;
    private Date createdWhen;
    private BigDecimal amount;
    private Long parentId;
    private BillStatus billStatus;
    private BillStyle billStyle;
    private long billNumber;
}
