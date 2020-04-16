package com.example.demo.service.Bill;

import com.example.demo.dto.Bill.CreateBillDto;
import com.example.demo.dto.Bill.UpdateBillDto;
import com.example.demo.integration.Bill.BillIntegrationService;
import com.example.demo.model.Bill.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {
    @Autowired
    BillIntegrationService billIntegrationService;

    public Bill createBill(CreateBillDto source){
        Bill bill = new Bill();
        bill.setBillStatus(source.billStatus);
        bill.setBillStyle(source.billStyle);
        bill.setBillNumber(source.billNumber);
        return bill;
    }

    public Bill getBill(Long id){
        return billIntegrationService.getBill(id);
    }

    public void updateBill(UpdateBillDto source){
        billIntegrationService.updateBill(source);
    }

    public void deleteBill(Long id){
        billIntegrationService.deleteBill(id);
    }
}
