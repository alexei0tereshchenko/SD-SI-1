package com.example.demo.controller;

import com.example.demo.dto.customer.CreateCustomerDto;
import com.example.demo.model.customer.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping(value = "/create")
    public Customer createCustomer(@RequestBody CreateCustomerDto source) {
        return customerService.createCustomer(source);
    }

    @GetMapping(value = "/get/{id}")
    public Customer getCustomer(@PathVariable Long id) {
        Customer customer = new Customer();
        customer.setName("TEST CUSTOMER");
        return customer;
    }
}
