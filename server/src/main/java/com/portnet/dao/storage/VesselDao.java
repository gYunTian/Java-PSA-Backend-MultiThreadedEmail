package com.portnet.dao.storage;

import java.util.List;

import com.portnet.entity.storage.Vessel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "vessel" table to perform various operations
 */

@Repository
public interface VesselDao extends CrudRepository<Vessel, String> {
    /**
     * Additional custom method to find User with specified email
     * @param startDate
     * @param endDate
     */
    @Query("select v from Vessel v where v.bthgDt >= ?1 AND v.bthgDt <= ?2")
    List<Vessel> findByDate(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
