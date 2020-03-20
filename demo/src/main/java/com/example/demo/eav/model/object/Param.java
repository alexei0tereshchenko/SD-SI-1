package com.example.demo.eav.model.object;

import com.example.demo.eav.model.meta.Attribute;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class Param implements Serializable {
    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "attr_id")
    private Attribute attribute;

    @Id
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "object_id")
    private Object object;

    private String value;
}
