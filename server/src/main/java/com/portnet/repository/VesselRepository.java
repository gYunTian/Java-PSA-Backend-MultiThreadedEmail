package com.portnet.repository;

import java.util.List;

import com.portnet.entity.storage.Vessel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VesselRepository extends CrudRepository<Vessel, String> { 
    @Query("select v from Vessel v where v.bthgDt >= ?1 AND v.bthgDt <= ?2")
    List<Vessel> findByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
