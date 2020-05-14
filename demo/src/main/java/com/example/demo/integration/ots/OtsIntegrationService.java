package com.example.demo.integration.ots;

import com.example.demo.controller.PaymentController;
import com.example.demo.dto.ots.UpdateOtsDto;
import com.example.demo.dto.payment.CreatePaymentDto;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Param;
import com.example.demo.model.ots.Ots;
import com.example.demo.eav.model.object.Object;
import com.example.demo.model.payment.PaymentMethod;
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
public class OtsIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    @Qualifier("ots")
    private EavBaseConverter otsConverter;

    @Autowired
    private PaymentController paymentController;

    @Transactional
    public void createOts(Ots source) throws OtsIntegrationException {
        if (objectRepository.countObjectsByParent(source.getParentId(), source.getObjectTypeId()) >= Ots.OTS_LIMIT_FOR_BILL_ACCOUNT) {
            throw new OtsIntegrationException("Can't create ots connected for billing account with ID:" + source.getParentId()
                    + ", because limit of ots for this billing account has already been reached");
        }
        else{
            Object otsDataObject = objectRepository.save(otsConverter.convertToEav(source));
            otsDataObject.getParams().forEach(
                    param -> {
                        param.setObject(otsDataObject);
                        paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                    });
        }
    }

    public Ots getOts(Long objectId) throws OtsIntegrationException {
        try{
            Object otsObject = objectRepository.findById(objectId).get();
            return (Ots)otsConverter.convertFromEav(otsObject);
        }
        catch(NoSuchElementException e){
            throw new OtsIntegrationException("Can't find ots with ID:" + objectId, e);
        }
    }

    public List<Ots> getBillingAccountOts(Long accountId) throws OtsIntegrationException {
        List<Ots> ots = new ArrayList<>();
        try{
            List<Object> results = new ArrayList<>();
            objectRepository.selectObjectsForParent(accountId, 5L).forEach(results::add);
            for (Object otsObject:results) {
                ots.add((Ots)otsConverter.convertFromEav(otsObject));
            }
            return ots;
        }
        catch(NoSuchElementException e){
            throw new OtsIntegrationException("Can't find ots for account with ID:" + accountId, e);
        }
    }

    @Transactional
    public void deleteOts(Long objectId) throws OtsIntegrationException {
        try{
            try{
                Object otsObject = objectRepository.findById(objectId).get();
                List<Param> params = otsObject.getParams();

                CreatePaymentDto dto = new CreatePaymentDto();
                BigDecimal amount = new BigDecimal(findParamByAttributeId(params, 3L).getValue());
                dto.setAmount(amount.negate());
                dto.setCreatedBy("OTS_TERMINATION");
                dto.setParentId(otsObject.getParentObject().getObjectId());
                dto.setPaymentMethod(PaymentMethod.BANK_CARD);

                paymentController.createPayment(dto);
            }
            catch (PaymentController.PaymentControllerException e){
                throw new OtsIntegrationException("Can't delete ots with ID:" + objectId + " because " + e.getMessage(), e);
            }
            paramRepository.deleteParamsForObject(objectId);
            objectRepository.deleteObject(objectId);
        }
        catch (DataAccessException e){
            throw new OtsIntegrationException("Can't delete ots with ID:" + objectId + " because of DataAccessException", e);
        }
    }

    @Transactional
    public Ots updateOts(UpdateOtsDto source, Long objectId) throws OtsIntegrationException {
        try{
            Object otsObject = objectRepository.findById(objectId).get();
            List<Param> params = otsObject.getParams();
            String otsStatus = findParamByAttributeId(params, 21L).getValue();
            if(otsStatus.equals("TERMINATED")){
                throw new OtsIntegrationException("Can't update ots because it's already terminated");
            }

            findParamByAttributeId(params, 14L).setValue(source.getDescription());
            findParamByAttributeId(params, 15L).setValue(otsConverter.getDateFormatter().format(source.getCreatedWhen()));
            findParamByAttributeId(params, 3L).setValue(source.getAmount().toString());
            findParamByAttributeId(params, 21L).setValue(source.getOtsStatus().toString());
            findParamByAttributeId(params, 22L).setValue(source.getTerminationReason());
            findParamByAttributeId(params, 23L).setValue(source.getInstalments().toString());
            findParamByAttributeId(params, 18L).setValue(otsConverter.getDateFormatter().format(source.getCancellationDate()));
            Ots ots = (Ots)otsConverter.convertFromEav(otsObject);
            objectRepository.save(otsObject);
            return ots;
        }
        catch (NoSuchElementException e){
            throw new OtsIntegrationException("Can't update ots because object with ID:" + objectId + " wasn't found", e);
        }
    }

    @Transactional
    public Ots terminateOts(Long objectId) throws OtsIntegrationException {
        try{
            Object otsObject = objectRepository.findById(objectId).get();
            List<Param> params = otsObject.getParams();
            String otsStatus = findParamByAttributeId(params, 21L).getValue();
            if(otsStatus.equals("TERMINATED")){
                throw new OtsIntegrationException("Can't terminate ots because it's already terminated");
            }

            findParamByAttributeId(params, 21L).setValue("TERMINATED");
            findParamByAttributeId(params, 18L).setValue(otsConverter.getDateFormatter().format(new Date()));
            Ots ots = (Ots)otsConverter.convertFromEav(otsObject);
            objectRepository.save(otsObject);
            return ots;
        }
        catch (NoSuchElementException e){
            throw new OtsIntegrationException("Can't update ots because object with ID:" + objectId + " wasn't found", e);
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

    public static class OtsIntegrationException extends Exception {
        public OtsIntegrationException(String msg, Exception cause) {
            super(msg, cause);
        }

        public OtsIntegrationException(String msg) {
            super(msg);
        }
    }
}
