package com.example.demo.service.Bill;

import com.example.demo.dto.bill.CreateBillDto;
import com.example.demo.dto.bill.UpdateBillDto;
import com.example.demo.integration.bill.BillIntegrationService;
import com.example.demo.model.Bill.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BillService {

    @Autowired
    private BillIntegrationService billIntegrationService;

    public Bill createBill(CreateBillDto source) throws Exception{
        Bill bill = new Bill();
            bill.setBillStatus(source.billStatus);
            bill.setBillStyle(source.billStyle);
            bill.setBillNumber(source.billNumber);
            billIntegrationService.createBill(bill);
            return bill;
    }
    public void updateBill(UpdateBillDto source, Long id) throws Exception {
        try {
            billIntegrationService.updateBill(id, source);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public Bill getBill(Long id) throws Exception {
        try {
            return billIntegrationService.getBill(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public void deleteBill(Long id) throws Exception {
        try {
            billIntegrationService.deleteBill(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    public void cancelBill(Long id) throws Exception {
        try {
            billIntegrationService.cancelBill(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}