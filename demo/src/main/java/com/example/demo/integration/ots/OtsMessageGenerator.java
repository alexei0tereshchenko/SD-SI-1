package com.example.demo.integration.ots;

import com.example.demo.integration.ExceptionMessageGenerator;
import org.springframework.stereotype.Component;

@Component
public class OtsMessageGenerator extends ExceptionMessageGenerator {
    OtsMessageGenerator(){
        this.setObjType("ots");
    }

    public String generateAlreadyTerminated(){
        return "Can't update ots because it's already terminated";
    }

    public String generatePaymentIntegrationException(Exception e, Long objectId){
        return "Can't delete ots with ID:" + objectId + " because " + e.getMessage();
    }
}
