package com.example.demo.integration.payment;

import com.example.demo.integration.ExceptionMessageGenerator;
import org.springframework.stereotype.Component;

@Component
public class PaymentMessageGenerator extends ExceptionMessageGenerator {
    PaymentMessageGenerator(){
        this.setObjType("payment");
    }

    public String generateAlreadyCancelled(){
        return "Can't update payment because it's already cancelled";
    }

    public String generateBillsAlreadyPaid(Long accountId){
        return "Can't create payment connected for billing account with ID:" + accountId + ", because all bills have already been paid";
    }

    public String generateNoBills(Long accountId){
        return "Can't create payment connected for billing account with ID:" + accountId + ", because there isn't any bills connected to this account";
    }
}
