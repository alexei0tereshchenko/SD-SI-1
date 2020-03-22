package com.example.demo.integration;

import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.model.Customer;
import com.example.demo.repository.ObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CustomerIntegrationService {

    @Autowired
    private ObjectRepository objectRepository;

    @Autowired
    private EavBaseConverter eavBaseConverter;

    @Transactional
    public void createCustomer(Customer source) {
        objectRepository.save(eavBaseConverter.convertToEav(source));
    }
}
