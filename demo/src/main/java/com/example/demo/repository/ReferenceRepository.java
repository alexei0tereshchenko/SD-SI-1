package com.example.demo.repository;

import com.example.demo.eav.model.object.Reference;
import com.example.demo.eav.model.object.ReferenceId;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReferenceRepository extends CrudRepository<Reference, ReferenceId> {

    @Modifying
    @Query(value = "INSERT into reference (object_id, attr_id, reference) VALUES (:object_id, :attr_id, :reference)", nativeQuery = true)
    void saveReference (@Param("object_id") Long objectId,
                        @Param("attr_id") Long attrId,
                        @Param("reference") Long reference);
}
