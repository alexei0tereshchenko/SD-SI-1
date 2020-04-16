package com.example.demo.controller;

import com.example.demo.dto.payment.CreatePaymentDto;
import com.example.demo.dto.payment.UpdatePaymentDto;
import com.example.demo.model.payment.Payment;
import com.example.demo.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public void putPayment(@RequestBody UpdatePaymentDto source, @PathVariable Long id) throws PaymentControllerException {
        try{ paymentService.updatePayment(source, id); }
        catch (PaymentService.PaymentServiceException e){ throw new PaymentControllerException(e); }
    }

    @GetMapping(value = "/{id}")
    public Payment getPayment(@PathVariable Long id) throws PaymentControllerException {
        try{ return paymentService.getPayment(id); }
        catch (PaymentService.PaymentServiceException e){ throw new PaymentControllerException(e); }
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePayment(@PathVariable Long id) throws PaymentControllerException{
        try{ paymentService.deletePayment(id); }
        catch (PaymentService.PaymentServiceException e){ throw new PaymentControllerException(e); }
    }

    public static class PaymentControllerException extends Exception {
        public PaymentControllerException(Exception cause) {
            super(cause.getMessage(), cause);
        }
    }
}
