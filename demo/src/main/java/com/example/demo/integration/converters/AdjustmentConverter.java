package com.example.demo.integration.converters;

import com.example.demo.annotations.Attribute;
import com.example.demo.eav.converter.EavBaseConverter;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.model.Base;
import com.example.demo.model.adjustment.Adjustment;
import com.example.demo.model.adjustment.AdjustmentStatus;
import com.example.demo.model.payment.PaymentMethod;
import com.example.demo.model.payment.PaymentStatus;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@Component
public class AdjustmentConverter extends EavBaseConverter {
    @Override
    public Base convertFromEav(Object source) {
        Adjustment adjustment = new Adjustment();
        adjustment.setName(source.getName());
        adjustment.setObjectId(source.getObjectId());
        adjustment.setObjectTypeId(source.getObjectType().getObjectTypeId());
        if (source.getParentObject() != null) {
            adjustment.setParentId(source.getParentObject().getObjectId());
        }
        List<Param> params = source.getParams();
        for (Param paramsValue:params) {
            Long attributeId = paramsValue.getAttribute().getAttributeId();
            String value = paramsValue.getValue();

            for (Field declaredField : adjustment.getClass().getDeclaredFields()) {
                if (declaredField.getDeclaredAnnotation(Attribute.class) != null
                        && attributeId.equals(Long.valueOf(declaredField.getDeclaredAnnotation(Attribute.class).attrId()))) {
                    declaredField.setAccessible(true);
                    try {
                        if (declaredField.getType() == AdjustmentStatus.class)
                        {
                            declaredField.set(adjustment, AdjustmentStatus.valueOf(value));
                        }
                        if (declaredField.getType() == Date.class)
                        {
                            declaredField.set(adjustment, EavBaseConverter.getDateFormatter().parse(value));
                        }
                        if (declaredField.getType() == String.class)
                        {
                            declaredField.set(adjustment, value);
                        }
                    } catch (ReflectiveOperationException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return adjustment;
    }
}
