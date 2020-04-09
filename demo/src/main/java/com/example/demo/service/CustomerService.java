package com.example.demo.service;

import com.example.demo.dto.customer.CreateCustomerDto;
import com.example.demo.integration.CustomerIntegrationService;
import com.example.demo.model.customer.Customer;
import com.example.demo.model.customer.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    CustomerIntegrationService customerIntegrationServiceInt;

    public Customer createCustomer(CreateCustomerDto source) {
        Customer customer = new Customer();
        customer.setFirstName(source.getFirstName());
        customer.setLastName(source.getLastName());
        customer.setStatus(Status.ACTIVE);
        customer.setName(customer.getFirstName() + " " + customer.getLastName());
        customerIntegrationServiceInt.createCustomer(customer);
        return customer;
    }
}
