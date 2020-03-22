package com.example.demo.controller;

import com.example.demo.dto.CreateCustomerDto;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/customer/create")
    public Customer createCustomer(@RequestBody CreateCustomerDto source) {
        return customerService.createCustomer(source);
    }

}
