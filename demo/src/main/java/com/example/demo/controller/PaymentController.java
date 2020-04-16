package com.example.demo.controller;

import com.example.demo.dto.payment.CreatePaymentDto;
import com.example.demo.dto.payment.UpdatePaymentDto;
import com.example.demo.model.payment.Payment;
import com.example.demo.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController()
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment createPayment(@RequestBody CreatePaymentDto source){
        return paymentService.createPayment(source);
    }

    @PutMapping(value = "/{id}")
    public void putPayment(@RequestBody UpdatePaymentDto source, @PathVariable Long id) throws PaymentService.PaymentServiceException {
        paymentService.updatePayment(source, id);
    }

    @GetMapping(value = "/{id}")
    public Payment getPayment(@PathVariable Long id){
        return paymentService.getPayment(id);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePayment(@PathVariable Long id){
        paymentService.deletePayment(id);
    }
}
