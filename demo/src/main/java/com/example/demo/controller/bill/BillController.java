package com.example.demo.controller.bill;

import com.example.demo.dto.bill.CreateBillDto;
import com.example.demo.dto.bill.UpdateBillDto;
import com.example.demo.model.Bill.Bill;
import com.example.demo.service.Bill.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/bill")
@CrossOrigin
public class BillController {

    @Autowired
    private BillService billService;

    @PostMapping(value = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Bill createBill(@RequestBody CreateBillDto source) throws BillController.BillControllerException {
        try {
            return billService.createBill(source);
        } catch (BillService.BillServiceException e) {
            throw new BillController.BillControllerException(e);
        }
    }

    @PutMapping(value = "/{id}")
    public Bill putBill(@RequestBody UpdateBillDto source, @PathVariable Long id) throws BillController.BillControllerException {
        try {
            return billService.updateBill(source, id);
        } catch (BillService.BillServiceException e) {
            throw new BillController.BillControllerException(e);
        }
    }

    @PutMapping(value = "/cancel/{id}")
    public Bill cancelBill(@PathVariable Long id) throws BillController.BillControllerException {
        try {
            return billService.cancelBill(id);
        } catch (BillService.BillServiceException e) {
            throw new BillController.BillControllerException(e);
        }
    }

    @GetMapping(value = "/{id}")
    public Bill getBill(@PathVariable Long id) throws BillController.BillControllerException {
        try {
            return billService.getBill(id);
        } catch (BillService.BillServiceException e) {
            throw new BillController.BillControllerException(e);
        }
    }

    @GetMapping(value = "account/{accoundId}")
    public List<Bill> getBillingAccountBill(@PathVariable Long accountId) throws BillController.BillControllerException {
        try {
            return billService.getBillingAccountBill(accountId);
        } catch (BillService.BillServiceException e) {
            throw new BillController.BillControllerException(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBill(@PathVariable Long id) throws BillController.BillControllerException {
        try {
            billService.deleteBill(id);
        } catch (BillService.BillServiceException e) {
            throw new BillController.BillControllerException(e);
        }
    }

    public static class BillControllerException extends Exception {
        public BillControllerException(Exception cause) {
            super(cause.getMessage(), cause);
        }
    }
}