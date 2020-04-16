package com.example.demo.repository;

import com.example.demo.eav.model.object.Object;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ObjectRepository extends CrudRepository<Object, Long> {
    @Modifying
    @Query(value = "DELETE FROM object WHERE (object_id = :objectId)", nativeQuery = true)
    void deleteObject(@org.springframework.data.repository.query.Param("objectId") Long objectId);
}
