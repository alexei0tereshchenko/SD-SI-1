package com.example.demo.service;

import com.example.demo.dto.payment.CreatePaymentDto;
import com.example.demo.dto.payment.UpdatePaymentDto;
import com.example.demo.integration.PaymentIntegrationService;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.payment.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentService {

    @Autowired
    private PaymentIntegrationService paymentIntegrationServiceInt;

    public Payment createPayment(CreatePaymentDto source){
        Payment payment = new Payment();

        //default parameters
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedWhen(new Date());
        payment.setCancellationDate(new Date());
        payment.setDescription("DEFAULT_DESCRIPTION");

        //custom parameters
        payment.setParentId(source.getParentId()); // temp
        payment.setAmount(source.getAmount());
        payment.setCreatedBy(source.getCreatedBy());
        payment.setPaymentMethod(source.getPaymentMethod());

        paymentIntegrationServiceInt.createPayment(payment);
        return payment;
    }

    public Payment getPayment(Long id){
       return paymentIntegrationServiceInt.getPayment(id);
    }

    public void updatePayment(UpdatePaymentDto source, Long id) throws PaymentServiceException {
        try{
            paymentIntegrationServiceInt.updatePayment(source, id);
        }
        catch (Exception e){
            throw new PaymentServiceException("Exception during integration", e);
        }
    }

    public void deletePayment(Long id){
        paymentIntegrationServiceInt.deletePayment(id);
    }

    public static class PaymentServiceException extends Exception {
        public PaymentServiceException(String msg, Exception cause) {
            super(msg, cause);
        }
    }
}
