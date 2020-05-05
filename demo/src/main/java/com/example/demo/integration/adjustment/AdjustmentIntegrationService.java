package com.example.demo.integration.adjustment;

import com.example.demo.dto.adjustment.UpdateAdjustmentDto;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.integration.converters.AdjustmentConverter;
import com.example.demo.model.adjustment.Adjustment;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AdjustmentIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private EavBaseConverter eavBaseConverter;
    @Autowired
    private AdjustmentConverter adjustmentConverter;

    @Transactional
    public void createAdjustment(Adjustment source) throws Exception {
        if (objectRepository.countObjectsByParent(source.getParentId(), source.getObjectTypeId()) >= Adjustment.ADJUSTMENT_LIMIT)
        {
            throw new Exception("The operation for billing account with ID : " + source.getParentId() + "was aborted because the limit was exceeded");
        }
        else
        {
            Object customerDataObject = objectRepository.save(eavBaseConverter.convertToEav(source));
            customerDataObject.getParams().forEach(
                    param -> {
                        param.setObject(customerDataObject);
                        paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                    });
        }
    }
    @Transactional
    public Adjustment getAdjustment(long objectId) throws Exception {
        try {
            Object adjustmentObject = objectRepository.findById(objectId).get();
            return (Adjustment)adjustmentConverter.convertFromEav(adjustmentObject);
        } catch (NoSuchElementException e) {
            throw new Exception("Error when searching adjustment : ",e);
        }
    }
    @Transactional
    public void deleteAdjustment(long objectId) throws Exception {
        try {
            paramRepository.deleteParamsForObject(objectId);
            objectRepository.deleteObject(objectId);
        } catch (DataAccessException e) {
            throw new Exception("Error during deletion adjustment : ",e);
        }
    }
    public void updateAdjustment(long objectId, UpdateAdjustmentDto source) throws Exception {
        try {
            Object adjustmentObject = objectRepository.findById(objectId).get();
            List<Param> params = adjustmentObject.getParams();
            String adjustmentStatus = "";
            for(Param param: params){
                if(param.getAttribute().getAttributeId().equals(25L)){
                    adjustmentStatus = param.getValue();
                }
            }
            if (adjustmentStatus.equals("PENDING") || adjustmentStatus.equals("APPROVED")){
                adjustmentObject.setName(source.getName());
                adjustmentObject.setParentObject(objectRepository.findById(source.getParentId()).get());
                for(Param param: params){
                    if (param.getAttribute().getAttributeId().equals(25L)){
                        param.setValue(source.getAdjustmentStatus().toString());
                    }
                    if (param.getAttribute().getAttributeId().equals(26L)){
                        param.setValue(EavBaseConverter.getDateFormatter().format(source.getApprovedDate()));
                    }
                    if (param.getAttribute().getAttributeId().equals(27L)) {
                        param.setValue(source.getRejectedReason());
                    }
                }
                objectRepository.save(adjustmentObject);
            }
            else{
                throw new Exception("Not possible to change the information");
            }
        }
        catch (NoSuchElementException e) {
            throw new Exception("Error during update: ",e);
        }
    }
    @Transactional
    public void cancelAdjustment(Long objectId){
        try {
            Object adjustmentObject = objectRepository.findById(objectId).get();
            List<Param> params = adjustmentObject.getParams();
            String adjustmentStatus = "";
            for(Param param: params){
                if(param.getAttribute().getAttributeId().equals(25L)){
                    adjustmentStatus = param.getValue();
                }
            }
            if (adjustmentStatus.equals("APPROVED")){
                for(Param param: params){
                    if(param.getAttribute().getAttributeId().equals(25L)){
                        param.setValue("CANCELLED");
                    }
                }
            }
            else{
                throw new Exception("Not possible to cancel");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
