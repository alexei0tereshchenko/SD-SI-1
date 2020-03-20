package com.example.demo.eav.model.meta;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attributeId;

    private AttrType attrType;


}
