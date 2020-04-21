package com.example.demo.integration.converters;

import com.example.demo.annotations.Attribute;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.model.Base;
import com.example.demo.model.payment.Payment;
import com.example.demo.model.payment.PaymentMethod;
import com.example.demo.model.payment.PaymentStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component("payment")
public class PaymentConverter extends EavBaseConverter {

    @Override
    public Base convertFromEav(Object source) {
        Payment payment = new Payment();
        payment.setName(source.getName());
        payment.setObjectId(source.getObjectId());
        payment.setObjectTypeId(source.getObjectType().getObjectTypeId());
        if (source.getParentObject() != null) {
            payment.setParentId(source.getParentObject().getObjectId());
        }

        List<Param> params = source.getParams();
        for (Param param : params) {
            Long attributeId = param.getAttribute().getAttributeId();
            String value = param.getValue();

            for (Field declaredField : payment.getClass().getDeclaredFields()) {
                if (declaredField.getDeclaredAnnotation(Attribute.class) != null
                        && attributeId.equals(Long.valueOf(declaredField.getDeclaredAnnotation(Attribute.class).attrId()))) {
                    declaredField.setAccessible(true);
                    try {
                        if (declaredField.getType() == String.class) {
                            declaredField.set(payment, value);
                        } else if (declaredField.getType() == Date.class) {
                            declaredField.set(payment, EavBaseConverter.getDateFormatter().parse(value));
                        } else if (declaredField.getType() == BigDecimal.class) {
                            declaredField.set(payment, new BigDecimal(value));
                        } else if (declaredField.getType() == PaymentStatus.class) {
                            declaredField.set(payment, PaymentStatus.valueOf(value));
                        } else if (declaredField.getType() == PaymentMethod.class) {
                            declaredField.set(payment, PaymentMethod.valueOf(value));
                        }
                    } catch (IllegalAccessException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return payment;
    }
}
