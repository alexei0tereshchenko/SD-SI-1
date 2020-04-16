package com.example.demo.repository;

import com.example.demo.eav.model.object.Param;
import com.example.demo.eav.model.object.ParamId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ParamRepository extends CrudRepository<Param, ParamId> {


    @Modifying
    @Query(value = "INSERT into param (value, object_id, attr_id) VALUES (:p_value, :object_id, :attr_id)", nativeQuery = true)
    void saveParam(@org.springframework.data.repository.query.Param("p_value") String value,
                              @org.springframework.data.repository.query.Param("object_id") Long objectId,
                              @org.springframework.data.repository.query.Param("attr_id") Long attrId);

    @Modifying
    @Query(value = "DELETE FROM param WHERE (object_id = :objectId)", nativeQuery = true)
    void deleteParamsForObject(@org.springframework.data.repository.query.Param("objectId") Long objectId);
}
