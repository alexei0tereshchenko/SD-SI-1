package com.example.demo.controller;

import com.example.demo.dto.payment.CreatePaymentDto;
import com.example.demo.dto.payment.UpdatePaymentDto;
import com.example.demo.model.payment.Payment;
import com.example.demo.service.payment.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/payment")
@CrossOrigin
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Payment createPayment(@RequestBody CreatePaymentDto source) throws PaymentControllerException{
        try{ return paymentService.createPayment(source); }
        catch (PaymentService.PaymentServiceException e){ throw new PaymentControllerException(e); }
    }

    @PutMapping(value = "/{id}")
    public Payment putPayment(@RequestBody UpdatePaymentDto source, @PathVariable Long id) throws PaymentControllerException {
        try{ return paymentService.updatePayment(source, id); }
        catch (PaymentService.PaymentServiceException e){ throw new PaymentControllerException(e); }
    }

    @PutMapping(value = "/cancel/{id}")
    public Payment cancelPayment(@PathVariable Long id) throws PaymentControllerException {
        try{ return paymentService.cancelPayment(id); }
        catch (PaymentService.PaymentServiceException e){ throw new PaymentControllerException(e); }
    }

    @GetMapping(value = "/{id}")
    public Payment getPayment(@PathVariable Long id) throws PaymentControllerException {
        try{ return paymentService.getPayment(id); }
        catch (PaymentService.PaymentServiceException e){ throw new PaymentControllerException(e); }
    }

    @GetMapping(value = "/account/{accountId}")
    public List<Payment> getBillingAccountPayments(@PathVariable Long accountId) throws PaymentControllerException {
        try{ return paymentService.getBillingAccountPayments(accountId); }
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
