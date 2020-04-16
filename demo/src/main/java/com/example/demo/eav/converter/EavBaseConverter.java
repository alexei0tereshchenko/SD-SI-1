package com.example.demo.eav.converter;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.eav.model.object.Reference;
import com.example.demo.model.Base;
import com.example.demo.model.Status;
import com.example.demo.model.payment.*;
import com.example.demo.repository.ObjectRepository;
import com.example.demo.repository.ObjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public abstract class EavBaseConverter {

    @Autowired
    private ObjectTypeRepository objectTypeRepository;

    @Autowired
    private ObjectRepository objectRepository;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public Object convertToEav(Base source) {
        Object target = new Object();

        target.setName(source.getName());

        target.setObjectType(objectTypeRepository.findById(
                Long.valueOf(source.getClass().getDeclaredAnnotation(ObjectType.class).objectTypeId())
        ).orElse(null));

        target.setObjectId(source.getObjectId());
        if (source.getParentId() != null)
            target.setParentObject(objectRepository.findById(source.getParentId()).orElse(null));
        List<Param> params = new ArrayList<>();

        List<Reference> references = new ArrayList<>();
        for (Field declaredField : source.getClass().getDeclaredFields()) {
            if (declaredField.getDeclaredAnnotation(Attribute.class) != null) {
                declaredField.setAccessible(true);
                com.example.demo.eav.model.meta.Attribute attribute = new com.example.demo.eav.model.meta.Attribute();
                attribute.setAttributeId(Long.valueOf(declaredField.getDeclaredAnnotation(Attribute.class).attrId()));
                Param param = new Param();
                param.setAttribute(attribute);
                param.setObject(target);
                try {
                    if(declaredField.getType() == Date.class){
                        param.setValue(dateFormatter.format(declaredField.get(source)));
                    }
                    else {
                        param.setValue(declaredField.get(source).toString());
                    }
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                params.add(param);
            }

            if (declaredField.getDeclaredAnnotation(com.example.demo.annotations.Reference.class) != null) {
                declaredField.setAccessible(true);
                com.example.demo.eav.model.meta.Attribute attribute = new com.example.demo.eav.model.meta.Attribute();
                attribute.setAttributeId(Long.valueOf(declaredField.getDeclaredAnnotation(com.example.demo.annotations.Reference.class).attrId()));
                Reference reference = new Reference();
                reference.setAttribute(attribute);
                reference.setObject(target);
                Object referenceObj = new Object();
                try {
                    referenceObj.setObjectId(((Base) declaredField.get(source)).getObjectId());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                reference.setReference(referenceObj);
                references.add(reference);
            }
        }
        target.setParams(params);
        target.setReferences(references);

        return target;
    }

    public <T extends Base> T convertFromEav(Object source, T target){
        target.setName(source.getName());
        target.setObjectId(source.getObjectId());
        target.setObjectTypeId(source.getObjectType().getObjectTypeId());
        if(source.getParentObject() != null) {
            target.setParentId(source.getParentObject().getObjectId());
        }

        List<Param> params = source.getParams();

        for (Param param : params){
            Long attributeId = param.getAttribute().getAttributeId();
            String value = param.getValue();

            for(Field declaredField : target.getClass().getDeclaredFields()){
                if(declaredField.getDeclaredAnnotation(Attribute.class) != null
                        && attributeId.equals(Long.valueOf(declaredField.getDeclaredAnnotation(Attribute.class).attrId()))){
                    declaredField.setAccessible(true);
                    try {
                        if(declaredField.getType() == String.class) {
                            declaredField.set(target, value);
                        }
                        else if(declaredField.getType() == Date.class){
                            declaredField.set(target, dateFormatter.parse(value));
                        }
                        else if(declaredField.getType() == BigDecimal.class) {
                            declaredField.set(target, new BigDecimal(value));
                        }
                        else if(declaredField.getType() == PaymentStatus.class) {
                            declaredField.set(target, PaymentStatus.valueOf(value));
                        }
                        else if(declaredField.getType() == PaymentMethod.class) {
                            declaredField.set(target, PaymentMethod.valueOf(value));
                        }
                        else if(declaredField.getType() == Status.class) {
                            declaredField.set(target, Status.valueOf(value));
                        }
                    } catch (IllegalAccessException | ParseException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            List<Reference> references = source.getReferences();

            // add code for reference parsing
        }

        return target;
    }

    // returns declared attribute_id for the field by its name
    public <T extends Base> Long getAttributeIdForField(T target, String fieldName) throws NoSuchFieldException {
        if(target.getClass().getDeclaredField(fieldName).getDeclaredAnnotation(Attribute.class) != null){
            return Long.valueOf(target.getClass().getDeclaredField(fieldName).getDeclaredAnnotation(Attribute.class).attrId());
        }
        else{
            return -1L;
        }
    }

}
