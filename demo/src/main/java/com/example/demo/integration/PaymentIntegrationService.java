package com.example.demo.integration;

import com.example.demo.dto.payment.UpdatePaymentDto;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.model.payment.Payment;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private EavBaseConverter eavBaseConverter;

    @Transactional
    public void createPayment(Payment source){
        Object paymentDataObject = objectRepository.save(eavBaseConverter.convertToEav(source));
        paymentDataObject.getParams().forEach(
                param -> {
                    param.setObject(paymentDataObject);
                    paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                });
    }

    @Transactional
    public void deletePayment(Long id){
    }

    @Transactional
    public void updatePayment(UpdatePaymentDto source){
    }

    @Transactional
    public Payment getPayment(Long id){
       Payment p = new Payment();
       p.setDescription("GET_REQUEST_TEST");
       return p;
    }
}
