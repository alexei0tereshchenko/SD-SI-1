package com.example.demo.integration.bill;

import com.example.demo.dto.bill.UpdateBillDto;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Param;
import com.example.demo.integration.converters.BillConverter;
import com.example.demo.model.Bill.Bill;
import com.example.demo.eav.model.object.Object;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class BillIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private EavBaseConverter eavBaseConverter;

    @Autowired
    private BillConverter billConverter;

    @Transactional
    public void createBill(Bill source) throws Exception {
        if (objectRepository.countObjectsByParent(source.getParentId(), source.getObjectTypeId()) >= Bill.BILL_LIMIT) {
            throw new Exception("The operation for billing account with ID : " + source.getParentId() + "was stopped due to exceeding the limit");
        } else {
            Object customerDataObject = objectRepository.save(eavBaseConverter.convertToEav(source));
            customerDataObject.getParams().forEach(
                    param -> {
                        param.setObject(customerDataObject);
                        paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                    });
        }
    }
    @Transactional
    public Bill getBill(long objectId) throws Exception {
        try {
            Object billobject = objectRepository.findById(objectId).get();
            return (Bill)billConverter.convertFromEav(billobject);
        } catch (NoSuchElementException e) {
            throw new Exception ("Error when searching bill : ",e);
        }
    }
    @Transactional
    public void deleteBill(long objectId) throws Exception {
        try {
            paramRepository.deleteParamsForObject(objectId);
            objectRepository.deleteObject(objectId);
        } catch (DataAccessException e) {
            throw new Exception("Error during deletion bill :",e);
        }
    }
    public void updateBill(long objectId, UpdateBillDto source) throws Exception {
        try {
            Object billObject = objectRepository.findById(objectId).get();
            List<Param> params = adjustmentObject.getParams();
            String billStatus = "";
            for(Param param: params){
                if(param.getAttribute().getAttributeId().equals(28L)){
                    billStatus = param.getValue();
                }
            }
            if (billStatus.equals("CREATED") || billStatus.equals("PENDING CANCELLETION")){
                billObject.setName(source.getName());
                billObject.setParentObject(ObjectRepository.findById(source.getParentId().get()));
                for(Param param: params){
                    if (param.getAttribute().getAttributeId().equals(28L)){
                        param.setValue(source.getBillStatus().toString());
                    }
                    if (param.getAttribute().getAttributeId().equals(29L)){
                        param.setValue(source.getBillStyle().toString());
                    }
                    if (param.getAttribute().getAttributeId().equals(30)){
                        param.setValue(source.getBillNumber());
                    }
                }
                objectRepository.save(billObject);
            }
            else {
                throw new Exception("Not possible to change the information");
            }
        } catch (NoSuchElementException e) {
            throw new Exception("Error during update: ",e);
        }
    }
    @Transactional
    public void cancelBill(Long objectId){
        try {
            Object billObject = objectRepository.findById(objectId).get();
            List<Param> params = billObject.getParams();
            String billStatus = "";
            for(Param param: params){
                if(param.getAttribute().getAttributeId().equals(28L)){
                    billStatus = param.getValue();
                }
            }
            if (billStatus.equals("CREATED")){
                for(Param param: params){
                    if(param.getAttribute().getAttributeId().equals(28L)){
                        param.setValue("CANCELLED");
                    }
                }
            }
            else {
                throw new Exception("Not possible to cancel");
            }
        } catch (exception e) {
            e.printStackTrace();
        }
    }
}