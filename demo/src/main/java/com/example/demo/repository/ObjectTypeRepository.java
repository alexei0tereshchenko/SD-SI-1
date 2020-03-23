package com.example.demo.repository;

import com.example.demo.eav.model.meta.ObjectType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectTypeRepository extends CrudRepository<ObjectType, Long> {
}
