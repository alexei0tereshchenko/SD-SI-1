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

import java.math.BigDecimal;
import java.util.ArrayList;
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

    static private final int PAYMENTS_LIMIT_FOR_BILL_ACCOUNT = 5;

    @Transactional
    public void createPayment(Payment source) throws PaymentIntegrationException {
        // we can't add payment if there isn't any bills
        if(objectRepository.countObjectsByParent(source.getParentId(), 7L) == 0){
            throw new PaymentIntegrationException("Can't create payment connected for billing account with ID:" + source.getParentId()
                    + ", because there isn't any bills connected to this account");
        }
        // we can't add non-negative payment if limit has already been reached
        else if ((source.getAmount().compareTo(BigDecimal.ZERO) > 0)
                && objectRepository.countObjectsWithNonNegativeAmountByParent(source.getParentId(), source.getObjectTypeId()) >= PAYMENTS_LIMIT_FOR_BILL_ACCOUNT) {
            throw new PaymentIntegrationException("Can't create payment connected for billing account with ID:" + source.getParentId()
                    + ", because limit of payments for this billing account has already been reached");
        }
        // we can't add non-negative payment if all bills have already been paid
        else if((source.getAmount().compareTo(BigDecimal.ZERO) > 0) &&
                objectRepository.totalAmountByParent(source.getParentId(), 7L).compareTo(
                        objectRepository.totalAmountByParent(source.getParentId(), 4L)) <= 0){
            throw new PaymentIntegrationException("Can't create payment connected for billing account with ID:" + source.getParentId()
                    + ", because all bills have already been paid");
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

    public List<Payment> getBillingAccountPayments(Long accountId) throws PaymentIntegrationException {
        List<Payment> payments = new ArrayList<>();
        try{
            List<Object> results = new ArrayList<>();
            objectRepository.selectObjectsForParent(accountId, 4L).forEach(results::add);
            for (Object paymentObject:results) {
                payments.add((Payment)paymentConverter.convertFromEav(paymentObject));
            }
            return payments;
        }
        catch(NoSuchElementException e){
            throw new PaymentIntegrationException("Can't find payments for account with ID:" + accountId, e);
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
    public Payment updatePayment(UpdatePaymentDto source, Long objectId) throws PaymentIntegrationException {
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
            Payment p = (Payment)paymentConverter.convertFromEav(paymentObject);
            objectRepository.save(paymentObject);
            return p;
        }
        catch (NoSuchElementException e){
            throw new PaymentIntegrationException("Can't update payment because object with ID:" + objectId + " wasn't found", e);
        }
    }

    @Transactional
    public Payment cancelPayment(Long objectId) throws PaymentIntegrationException {
        try{
            Object paymentObject = objectRepository.findById(objectId).get();
            List<Param> params = paymentObject.getParams();
            String paymentStatus = findParamByAttributeId(params, 6L).getValue();
            if(paymentStatus.equals("CANCELLED")){
                throw new PaymentIntegrationException("Can't cancel payment because it's already cancelled");
            }

            findParamByAttributeId(params, 6L).setValue("CANCELLED");
            findParamByAttributeId(params, 18L).setValue(paymentConverter.getDateFormatter().format(new Date()));
            Payment p = (Payment)paymentConverter.convertFromEav(paymentObject);
            objectRepository.save(paymentObject);
            return p;
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
