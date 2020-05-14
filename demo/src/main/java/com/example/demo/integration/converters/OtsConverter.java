package com.example.demo.integration.converters;

import com.example.demo.annotations.Attribute;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.model.Base;
import com.example.demo.model.ots.Ots;
import com.example.demo.model.ots.OtsStatus;
import com.example.demo.model.payment.PaymentStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component("ots")
public class OtsConverter extends EavBaseConverter {
    @Override
    public Base convertFromEav(Object source) {
        Ots ots = new Ots();
        ots.setName(source.getName());
        ots.setObjectId(source.getObjectId());
        ots.setObjectTypeId(source.getObjectType().getObjectTypeId());
        if (source.getParentObject() != null) {
            ots.setParentId(source.getParentObject().getObjectId());
        }

        List<Param> params = source.getParams();
        for (Param param : params) {
            Long attributeId = param.getAttribute().getAttributeId();
            String value = param.getValue();

            for (Field declaredField : ots.getClass().getDeclaredFields()) {
                if (declaredField.getDeclaredAnnotation(Attribute.class) != null
                        && attributeId.equals(Long.valueOf(declaredField.getDeclaredAnnotation(Attribute.class).attrId()))) {
                    declaredField.setAccessible(true);
                    try {
                        if (declaredField.getType() == String.class) {
                            declaredField.set(ots, value);
                        } else if (declaredField.getType() == Date.class) {
                            declaredField.set(ots, EavBaseConverter.getDateFormatter().parse(value));
                        } else if (declaredField.getType() == BigDecimal.class) {
                            declaredField.set(ots, new BigDecimal(value));
                        } else if (declaredField.getType() == OtsStatus.class) {
                            declaredField.set(ots, OtsStatus.valueOf(value));
                        } else if (declaredField.getType() == Boolean.class){
                            declaredField.set(ots, Boolean.valueOf(value));
                        }
                    } catch (IllegalAccessException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return ots;
    }
}
