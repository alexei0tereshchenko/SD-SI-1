package com.example.demo.dto.Bill;

import com.example.demo.model.Bill.BillStatus;
import com.example.demo.model.Bill.BillStyle;
import lombok.Data;

@Data
public class CreateBillDto {
    public BillStatus billStatus;
    public BillStyle billStyle;
    public long billNumber;
}
