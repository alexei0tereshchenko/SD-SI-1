package com.example.demo.integration;

import com.example.demo.eav.model.object.Object;
import com.example.demo.integration.converters.CustomerConverter;
import com.example.demo.model.customer.Customer;
import com.example.demo.repository.AttributeRepository;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ParamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("customer")
    private CustomerConverter customerConverter;

    @Transactional
    public void createCustomer(Customer source) {
        Object customerDataObject = objectRepository.save(customerConverter.convertToEav(source));
        customerDataObject.getParams().forEach(
                param -> {
                    param.setObject(customerDataObject);
                    paramRepository.saveParam(param.getValue(), param.getObject().getObjectId(), param.getAttribute().getAttributeId());
                });
    }
}
