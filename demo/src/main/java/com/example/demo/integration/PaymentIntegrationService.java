package com.example.demo.integration;

import com.example.demo.dto.payment.UpdatePaymentDto;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.model.payment.Payment;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
public class PaymentIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    private EavBaseConverter eavBaseConverter;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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
    public void deletePayment(Long objectId){
        paramRepository.deleteParamsForObject(objectId);
        objectRepository.deleteObject(objectId);
    }

    public static class PaymentIntegrationException extends Exception {
        public PaymentIntegrationException(String msg, Exception cause) {
            super(msg, cause);
        }
    }

    // doesn't update references
    @Transactional
    public void updatePayment(UpdatePaymentDto source, Long objectId) throws PaymentIntegrationException {
        Object paymentObject = objectRepository.findById(objectId).get();
        //paymentObject.setParentObject(objectRepository.findById(source.getParentId()).get());
        List<Param> params = paymentObject.getParams();
        for(Field declaredField : source.getClass().getDeclaredFields()){
            String fieldName = declaredField.getName();
            try{
                Long attributeId = eavBaseConverter.getAttributeIdForField(new Payment(), fieldName);
                if(attributeId != -1){
                    for(Param param: params){
                        if(param.getAttribute().getAttributeId() == attributeId){
                            declaredField.setAccessible(true);
                            if(declaredField.getType() == Date.class){
                                param.setValue(dateFormatter.format(declaredField.get(source)));
                            }
                            else {
                                param.setValue(declaredField.get(source).toString());
                            }
                        }
                    }
                }
            }
            catch (NoSuchFieldException | IllegalAccessException e){
                throw new PaymentIntegrationException("Can't update payment" + source, e);
            }
        }

        objectRepository.save(paymentObject);
    }

    public Payment getPayment(Long objectId){
        Payment p = new Payment();
        Object paymentObject = objectRepository.findById(objectId).get();
        eavBaseConverter.convertFromEav(paymentObject, p);
        return p;
    }
}
