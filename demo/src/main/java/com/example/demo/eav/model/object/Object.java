package com.example.demo.eav.model.object;

import com.example.demo.eav.model.meta.ObjectType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Object {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long objectId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_id")
    private Object parentObject;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "object_type_id")
    private ObjectType objectType;

    private String name;

    @OneToMany(mappedBy = "object")
    private List<Param> params;

    @OneToMany(mappedBy = "object")
    private List<Reference> references;
}
