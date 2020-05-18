package com.example.demo.integration.converters;

import com.example.demo.annotations.Attribute;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.model.Base;
import com.example.demo.model.Bill.Bill;
import com.example.demo.model.Bill.BillStatus;
import com.example.demo.model.Bill.BillStyle;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component("bill")
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
        for (Param param : params) {
            Long attributeId = param.getAttribute().getAttributeId();
            String value = param.getValue();

            for (Field declaredField : bill.getClass().getDeclaredFields()) {
                if (declaredField.getDeclaredAnnotation(Attribute.class) != null
                        && attributeId.equals(Long.valueOf(declaredField.getDeclaredAnnotation(Attribute.class).attrId()))) {
                    declaredField.setAccessible(true);
                    try {
                        if (declaredField.getType() == String.class) {
                            declaredField.set(bill, value);
                        } else if (declaredField.getType() == Date.class) {
                            declaredField.set(bill, EavBaseConverter.getDateFormatter().parse(value));
                        } else if (declaredField.getType() == BigDecimal.class) {
                            declaredField.set(bill, new BigDecimal(value));
                        } else if (declaredField.getType() == BillStatus.class) {
                            declaredField.set(bill, BillStatus.valueOf(value));
                        } else if (declaredField.getType() == BillStyle.class) {
                            declaredField.set(bill, BillStyle.valueOf(value));
                        }
                    } catch (IllegalAccessException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return bill;
    }
}