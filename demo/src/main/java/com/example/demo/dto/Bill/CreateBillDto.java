package com.example.demo.dto.Bill;

import com.example.demo.model.Bill.BillStatus;
import lombok.Data;

@Data
public class CreateBillDto {
    public BillStatus billStatus;
    public enum billStyle;
    public long billNumber;
}
