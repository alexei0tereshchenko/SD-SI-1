package com.example.demo.integration.converters;

import com.example.demo.annotations.Attribute;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.model.Base;
import com.example.demo.model.Bill.Bill;
import com.example.demo.model.Bill.BillStatus;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
public class BillConverter extends EavBaseConverter {
    @Override
    public Base convertFromEav(Object source) {
        Bill bill = new Bill();
        bill.setName(source.getName());
        bill.setObjectId(source.getObjectId());
        bill.setObjectTypeId(source.getObjectType().getObjectTypeId());
        if (source.getParentObject() != null) {
            bill.setParentId(source.getParentObject().getObjectId());
        }
        List<Param> params = source.getParams();
        for (Param paramsValue:params) {
            Long attributeId = paramsValue.getAttribute().getAttributeId();
            String value = paramsValue.getValue();

            for (Field declaredField : bill.getClass().getDeclaredFields()) {
                if (declaredField.getDeclaredAnnotation(Attribute.class)) != null
                        && attributeId.equals(Long.valueOf(declaredField.getDeclaredAnnotation(Attribute.class).attrId)) {
                    declaredField.setAccessible(true);
                    try {
                        if (declaredField.getType() == BillStatus.class)
                        {
                            declaredField.set(bill, BillStatus.valueOf(value));
                        }
                        if (declaredField.getType() == Date.class)
                        {
                            declaredField.set(bill, EavBaseConverter.getDateFormatter().parse(value));
                        }
                        if (declaredField.getType() == String.class)
                        {
                            declaredField.set(bill, value);
                        }
                    } catch (ReflectiveOperationException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return bill;
    }
}