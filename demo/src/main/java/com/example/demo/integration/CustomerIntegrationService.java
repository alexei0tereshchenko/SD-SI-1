package com.example.demo.integration;

import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.model.customer.Customer;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerIntegrationService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private ParamRepository paramRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    @Autowired
    private EavBaseConverter eavBaseConverter;

    @Transactional
    public void createCustomer(Customer source) {
        Object customerDataObject = objectRepository.save(eavBaseConverter.convertToEav(source));
        customerDataObject.getParams().forEach(
                param -> {
                    param.setObject(customerDataObject);
                    paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                });
    }
}
