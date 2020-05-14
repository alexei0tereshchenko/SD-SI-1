package com.example.demo.repository;

import com.example.demo.eav.model.object.Object;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ObjectRepository extends CrudRepository<Object, Long> {
    @Modifying
    @Query(value = "DELETE FROM object WHERE (object_id = :objectId)", nativeQuery = true)
    void deleteObject(@org.springframework.data.repository.query.Param("objectId") Long objectId);

    @Query(value = "select count(*) from object where (parent_id = :parentId and object_type_id = :objectTypeId)", nativeQuery = true)
    int countObjectsByParent(@org.springframework.data.repository.query.Param("parentId") Long parentId,
                             @org.springframework.data.repository.query.Param("objectTypeId") Long objectTypeId);

    //count how many objects with non-negative amount exist for provided object type and parent
    @Query(value = "select count(*) from (object join param on (object.object_id = param.object_id and param.attr_id = 3)) where (parent_id = :parentId and object_type_id = :objectTypeId and param.value >= 0)", nativeQuery = true)
    int countObjectsWithNonNegativeAmountByParent(@org.springframework.data.repository.query.Param("parentId") Long parentId,
                                               @org.springframework.data.repository.query.Param("objectTypeId") Long objectTypeId);

    //count total amount for provided object type and parent
    @Query(value = "select sum(param.value) from (object join param on (object.object_id = param.object_id and param.attr_id = 3)) where (parent_id = :parentId and object_type_id = :objectTypeId)", nativeQuery = true)
    BigDecimal totalAmountByParent(@org.springframework.data.repository.query.Param("parentId") Long parentId,
                                   @org.springframework.data.repository.query.Param("objectTypeId") Long objectTypeId);

    @Query(value = "select * from object where (parent_id = :parentId and object_type_id = :objectTypeId)", nativeQuery = true)
    Iterable<Object> selectObjectsForParent(@org.springframework.data.repository.query.Param("parentId") Long parentId,
                                            @org.springframework.data.repository.query.Param("objectTypeId") Long objectTypeId);
}
