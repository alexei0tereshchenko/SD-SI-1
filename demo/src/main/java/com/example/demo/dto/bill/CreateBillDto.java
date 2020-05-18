package com.example.demo.dto.bill;

import com.example.demo.model.Bill.BillStatus;
import com.example.demo.model.Bill.BillStyle;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class CreateBillDto {
    private Long parentId;
    private BigDecimal amount;
    private Date createdWhen;
    public BillStatus billStatus;
    public BillStyle billStyle;
    public Long billNumber;
}
