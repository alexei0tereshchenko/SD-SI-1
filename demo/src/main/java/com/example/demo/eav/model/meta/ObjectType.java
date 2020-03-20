package com.example.demo.eav.model.meta;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class ObjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectTypeId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private ObjectType parentObjectType;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "attr_object_type", joinColumns = @JoinColumn(name = "object_type_id"),
            inverseJoinColumns = @JoinColumn(name = "attr_id"))
    private List<Attribute> attributes;

    private String name;
}
