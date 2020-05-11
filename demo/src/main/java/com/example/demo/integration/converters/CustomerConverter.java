package com.example.demo.integration.converters;

import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.model.Base;
import org.springframework.stereotype.Component;

@Component("customer")
public class CustomerConverter extends EavBaseConverter {

    @Override
    public Base convertFromEav(Object source) {
        return null;
    }
}
