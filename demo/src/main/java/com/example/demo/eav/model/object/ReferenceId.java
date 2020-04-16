package com.example.demo.eav.model.object;

import com.example.demo.eav.model.meta.Attribute;
import lombok.Data;

import java.io.Serializable;

@Data
public class ReferenceId implements Serializable {
    private Attribute attribute;
    private Object object;
}
