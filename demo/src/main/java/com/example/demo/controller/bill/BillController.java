package com.example.demo.controller.bill;

import com.example.demo.dto.bill.CreateBillDto;
import com.example.demo.dto.bill.UpdateBillDto;
import com.example.demo.model.Bill.Bill;
import com.example.demo.service.Bill.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Bill createBill(@RequestBody CreateBillDto source){
        return billService.createBill(source);
    }

    @PutMapping
    public void putBill(@RequestBody UpdateBillDto source){
        billService.updateBill(source);
    }

    @GetMapping(value = "/{id}")
    public Bill getBill(@PathVariable Long id){
        return billService.getBill(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBill(@PathVariable Long id){
        billService.deleteBill(id);
    }
}
