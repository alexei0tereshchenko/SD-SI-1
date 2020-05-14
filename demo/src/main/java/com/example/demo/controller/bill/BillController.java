package com.example.demo.controller.bill;

import com.example.demo.dto.bill.CreateBillDto;
import com.example.demo.dto.bill.UpdateBillDto;
import com.example.demo.model.Bill.Bill;
import com.example.demo.service.Bill.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class BillController {
    @Autowired
    private BillService billService;

    @RequestMapping("/bill/create")
    public Bill createBill(@RequestBody CreateBillDto source) throws Exception {
        try {
            return billService.createBill(source);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @PutMapping(value = "/{id}")
    public void updateBill(@RequestBody UpdateBillDto source, @PathVariable Long id) throws Exception {
        try {
            billService.updateBill(source,id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @PutMapping(value = "/bill/cancel/{id}")
    public void cancelBill(@PathVariable Long id) throws Exception {
        try {
            billService.cancelBill(id);
        } catch (exception e) {
            throw new Exception(e);
        }
    }
    @GetMapping(value = "/{id}")
    public Bill getBill(@PathVariable Long id) throws Exception {
        try {
            return billService.getBill(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
    @DeleteMapping(value = "/{id}")
    public void deleteBill(@PathVariable Long id) throws Exception {
        try {
            billService.deleteBill(id);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}