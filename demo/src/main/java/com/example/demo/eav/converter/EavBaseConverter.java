package com.example.demo.eav.converter;

import com.example.demo.annotations.Attribute;
import com.example.demo.annotations.ObjectType;
import com.example.demo.eav.model.object.Object;
import com.example.demo.eav.model.object.Param;
import com.example.demo.eav.model.object.Reference;
import com.example.demo.model.Base;
import com.example.demo.repository.ObjectTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Component
public class EavBaseConverter {

    @Autowired
    private ObjectTypeRepository objectTypeRepository;

    public Object convertToEav(Base source) {
        Object target = new Object();

        target.setName(source.getName());

        target.setObjectType(objectTypeRepository.findById(
                Long.valueOf(source.getClass().getDeclaredAnnotation(ObjectType.class).objectTypeId())
        ).orElse(null));

        target.setObjectId(source.getObjectId());

        List<Param> params = new ArrayList<>();

        List<Reference> references = new ArrayList<>();
        for (Field declaredField : source.getClass().getDeclaredFields()) {
            if (declaredField.getDeclaredAnnotation(Attribute.class) != null) {
                com.example.demo.eav.model.meta.Attribute attribute = new com.example.demo.eav.model.meta.Attribute();
                attribute.setAttributeId(Long.valueOf(declaredField.getDeclaredAnnotation(Attribute.class).attrId()));
                Param param = new Param();
                param.setAttribute(attribute);
                param.setObject(target);
                try {
                    param.setValue(declaredField.get(source).toString());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                params.add(param);
            }

            if (declaredField.getDeclaredAnnotation(com.example.demo.annotations.Reference.class) != null) {
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
}
