package com.example.demo.integration.adjustment;

import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.model.adjustment.Adjustment;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class AdjustmentIntegrationService {
    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    @Qualifier("customer")
    private EavBaseConverter eavBaseConverter;

    @Transactional
    public void createAdjustment(Adjustment source) {
        Object customerDataObject = objectRepository.save(eavBaseConverter.convertToEav(source));
        customerDataObject.getParams().forEach(
                param -> {
                    param.setObject(customerDataObject);
                    paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                });
    }
}
