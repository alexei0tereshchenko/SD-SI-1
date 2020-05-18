package com.example.demo.integration.bill;

import com.example.demo.dto.bill.UpdateBillDto;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Param;
import com.example.demo.model.Bill.Bill;
import com.example.demo.eav.model.object.Object;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BillIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    @Qualifier("bill")
    private EavBaseConverter billConverter;

    @Transactional
    public void createBill(Bill source) throws BillIntegrationException {
        if (objectRepository.countObjectsByParent(source.getParentId(), source.getObjectTypeId()) >= Bill.BILL_LIMIT_FOR_BILL_ACCOUNT) {
            throw new BillIntegrationException("The operation for billing account with ID : " + source.getParentId() + "was stopped due to exceeding the limit");
        } else {
            Object billDataObject = objectRepository.save(billConverter.convertToEav(source));
            billDataObject.getParams().forEach(
                    param -> {
                        param.setObject(billDataObject);
                        paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                    });
        }
    }

    public Bill getBill(long objectId) throws BillIntegrationException {
        try {
            Object billObject = objectRepository.findById(objectId).get();
            return (Bill) billConverter.convertFromEav(billObject);
        } catch (NoSuchElementException e) {
            throw new BillIntegrationException("Error when searching bill with ID : " + objectId, e);
        }
    }

    public List<Bill> getBillingAccountBill(Long accountId)
            throws BillIntegrationException {
        List<Bill> bill = new ArrayList<>();
        try {
            List<Object> results = new ArrayList<>();
            objectRepository.selectObjectsForParent(accountId, 7L).forEach(results::add);
            for (Object billObject : results) {
                bill.add((Bill) billConverter.convertFromEav(billObject));
            }
            return bill;
        } catch (NoSuchElementException e) {
            throw new BillIntegrationException("Can't find bill for account with ID:" + accountId, e);
        }
    }

    @Transactional
    public void deleteBill(long objectId) throws BillIntegrationException {
        try {
            paramRepository.deleteParamsForObject(objectId);
            objectRepository.deleteObject(objectId);
        } catch (DataAccessException e) {
            throw new BillIntegrationException("Can't delete payment with ID:" + objectId + "because of DataAccessException", e);
        }
    }

    @Transactional
    public Bill updateBill(UpdateBillDto source, Long objectId) throws BillIntegrationException {
        try {
            Object billObject = objectRepository.findById(objectId).get();
            List<Param> params = billObject.getParams();
            String billStatus = findParamByAttributeId(params, 28L).getValue();
            if (billStatus.equals("CANCELED")) {
                throw new BillIntegrationException("Can't update bill because it's already canceled");
            }

            findParamByAttributeId(params, 28L).setValue("CANCELED");
            findParamByAttributeId(params, 29L).setValue(source.getBillStyle().toString());
            findParamByAttributeId(params, 30L).setValue(source.getBillNumber().toString());
            Bill b = (Bill) billConverter.convertFromEav(billObject);
            objectRepository.save(billObject);
            return b;
        } catch (NoSuchElementException e) {
            throw new BillIntegrationException("Can't update bill because object with ID:" + objectId + "wasn't found", e);
        }
    }

    @Transactional
    public Bill cancelBill(Long objectId) throws BillIntegrationException {
        try {
            Object billObject = objectRepository.findById(objectId).get();
            List<Param> params = billObject.getParams();
            String billStatus = findParamByAttributeId(params, 28L).getValue();
            if (billStatus.equals("CANCELED")) {
                throw new BillIntegrationException("Can't cancel bill because it's already canceled");
            }
            findParamByAttributeId(params, 28L).setValue("CANCELED");
            Bill bill = (Bill) billConverter.convertFromEav(billObject);
            objectRepository.save(billObject);
            return bill;
        } catch (NoSuchElementException e) {
            throw new BillIntegrationException("Can't update bill because object with Id:" + objectId + " wasn't found", e);
        }
    }

    private Param findParamByAttributeId(List<Param> params, Long attributeId) {
        for (Param param : params) {
            if (param.getAttribute().getAttributeId().equals(attributeId)) {
                return param;
            }
        }
        return null;
    }

    public static class BillIntegrationException extends Exception {
        public BillIntegrationException(String msg, Exception cause) {
            super(msg, cause);
        }

        public BillIntegrationException(String msg) {
            super(msg);
        }
    }
}