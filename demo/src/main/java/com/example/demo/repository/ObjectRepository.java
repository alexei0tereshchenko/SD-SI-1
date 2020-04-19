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

    @Query(value = "select count(*) from object where (parent_id = :parentId and object_type_id = :objectTypeId)", nativeQuery = true)
    int countObjectsByParent(@org.springframework.data.repository.query.Param("parentId") Long parentId,
                             @org.springframework.data.repository.query.Param("objectTypeId") Long objectTypeId);
}
