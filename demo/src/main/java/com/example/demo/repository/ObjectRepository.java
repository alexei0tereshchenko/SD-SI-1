package com.example.demo.repository;

import com.example.demo.eav.model.object.Object;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectRepository extends CrudRepository<Object, Long> {
}
