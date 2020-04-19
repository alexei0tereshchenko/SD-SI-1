package com.example.demo.integration.payment;

import com.example.demo.dto.payment.UpdatePaymentDto;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.integration.converters.PaymentConverter;
import com.example.demo.model.payment.Payment;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PaymentIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    @Qualifier("payment")
    private PaymentConverter paymentConverter;

    @Transactional
    public void createPayment(Payment source) throws PaymentIntegrationException {
        if (objectRepository.countObjectsByParent(source.getParentId(), source.getObjectTypeId()) >= Payment.PAYMENTS_LIMIT_FOR_BILL_ACCOUNT) {
            throw new PaymentIntegrationException("Can't create payment connected for billing account with ID:" + source.getParentId()
                    + ", because limit of payments for this billing account has already been reached");
        }
        else{
            Object paymentDataObject = objectRepository.save(paymentConverter.convertToEav(source));
            paymentDataObject.getParams().forEach(
                    param -> {
                        param.setObject(paymentDataObject);
                        paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                    });
        }
    }

    public Payment getPayment(Long objectId) throws PaymentIntegrationException {
        try{
            Object paymentObject = objectRepository.findById(objectId).get();
            return (Payment)paymentConverter.convertFromEav(paymentObject);
        }
        catch(NoSuchElementException e){
            throw new PaymentIntegrationException("Can't find payment with ID:" + objectId, e);
        }
    }

    @Transactional
    public void deletePayment(Long objectId) throws PaymentIntegrationException {
        try{
            paramRepository.deleteParamsForObject(objectId);
            objectRepository.deleteObject(objectId);
        }
        catch (DataAccessException e){
            throw new PaymentIntegrationException("Can't delete payment with ID:" + objectId + " because of DataAccessException", e);
        }
    }

    @Transactional
    public void updatePayment(UpdatePaymentDto source, Long objectId) throws PaymentIntegrationException {
        try{
            Object paymentObject = objectRepository.findById(objectId).get();
            List<Param> params = paymentObject.getParams();
            String paymentStatus = findParamByAttributeId(params, 6L).getValue();
            if(paymentStatus.equals("CANCELLED")){
                throw new PaymentIntegrationException("Can't update payment because it's already cancelled");
            }

            paymentObject.setName(source.getName());
            paymentObject.setParentObject(objectRepository.findById(source.getParentId()).get());

            findParamByAttributeId(params, 14L).setValue(source.getDescription());
            findParamByAttributeId(params, 15L).setValue(paymentConverter.getDateFormatter().format(source.getCreatedWhen()));
            findParamByAttributeId(params, 3L).setValue(source.getAmount().toString());
            findParamByAttributeId(params, 6L).setValue(source.getStatus().toString());
            findParamByAttributeId(params, 16L).setValue(source.getPaymentMethod().toString());
            findParamByAttributeId(params, 17L).setValue(source.getCreatedBy().toString());
            findParamByAttributeId(params, 18L).setValue(paymentConverter.getDateFormatter().format(source.getCancellationDate()));
            objectRepository.save(paymentObject);
        }
        catch (NoSuchElementException e){
            throw new PaymentIntegrationException("Can't update payment because object with ID:" + objectId + " wasn't found", e);
        }
    }

    @Transactional
    public void cancelPayment(Long objectId) throws PaymentIntegrationException {
        try{
            Object paymentObject = objectRepository.findById(objectId).get();
            List<Param> params = paymentObject.getParams();
            String paymentStatus = findParamByAttributeId(params, 6L).getValue();
            if(paymentStatus.equals("CANCELLED")){
                throw new PaymentIntegrationException("Can't cancel payment because it's already cancelled");
            }

            findParamByAttributeId(params, 6L).setValue("CANCELLED");
            findParamByAttributeId(params, 18L).setValue(paymentConverter.getDateFormatter().format(new Date()));
            objectRepository.save(paymentObject);
        }
        catch (NoSuchElementException e){
            throw new PaymentIntegrationException("Can't update payment because object with ID:" + objectId + " wasn't found", e);
        }
    }

    private Param findParamByAttributeId(List<Param> params, Long attributeId){
        for(Param param: params){
            if(param.getAttribute().getAttributeId().equals(attributeId)){
                return param;
            }
        }
        return null;
    }

    public static class PaymentIntegrationException extends Exception {
        public PaymentIntegrationException(String msg, Exception cause) {
            super(msg, cause);
        }

        public PaymentIntegrationException(String msg) {
            super(msg);
        }
    }
}
