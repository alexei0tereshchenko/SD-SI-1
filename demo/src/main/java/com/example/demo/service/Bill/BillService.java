package com.example.demo.service.Bill;

import com.example.demo.dto.bill.CreateBillDto;
import com.example.demo.dto.bill.UpdateBillDto;
import com.example.demo.integration.bill.BillIntegrationService;
import com.example.demo.model.Bill.Bill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillService {

    @Autowired
    BillIntegrationService billIntegrationService;

    public Bill createBill(CreateBillDto source) throws BillServiceException {
        try {
            Bill bill = new Bill();

            bill.setObjectTypeId(7L);
            bill.setParentId(source.getParentId());
            bill.setAmount(source.getAmount());
            bill.setBillStatus(source.getBillStatus());
            bill.setBillStyle(source.getBillStyle());
            bill.setBillNumber(source.getBillNumber());

            billIntegrationService.createBill(bill);
            return bill;
        } catch (Exception e) {
            throw new BillServiceException(e);
        }
    }

    public Bill getBill(Long id) throws BillServiceException {
        try {
            return billIntegrationService.getBill(id);
        } catch (Exception e) {
            throw new BillServiceException(e);
        }
    }

    public List<Bill> getBillingAccountBill(Long accountId) throws BillServiceException {
        try {
            return billIntegrationService.getBillingAccountBill(accountId);
        } catch (Exception e) {
            throw new BillServiceException(e);
        }
    }

    public Bill updateBill(UpdateBillDto source, Long id) throws BillServiceException {
        try {
            return billIntegrationService.updateBill(source, id);
        } catch (Exception e) {
            throw new BillServiceException(e);
        }
    }

    public void deleteBill(Long id) throws BillServiceException {
        try {
            billIntegrationService.deleteBill(id);
        } catch (Exception e) {
            throw new BillServiceException(e);
        }
    }

    public Bill cancelBill(Long id) throws BillServiceException {
        try {
            return billIntegrationService.cancelBill(id);
        } catch (Exception e) {
            throw new BillServiceException(e);
        }
    }

    public static class BillServiceException extends Exception {
        public BillServiceException(Exception cause) {
            super(cause.getMessage(), cause);
        }
    }
}