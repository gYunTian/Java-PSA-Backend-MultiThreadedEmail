package com.vsta.dao;

import com.vsta.dto.VesselDTO;
import com.vsta.model.Vessel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object shared by "vessel" and "vesselDTO" tables. Used to perform
 * various operations on the database including retrieval and modification.
 */

@Repository
public interface VesselDAO extends JpaRepository<Vessel, String> {

    /**
     * startDate and endDate cannot be same and endDate must minimally be 1 day
     * later.
     *
     * @param startDate Date with format YYYY-MM-DD to start retrieving vessels from.
     * @param endDate   Date with format YYYY-MM-DD that retrieval of vessels is to
     *                  be done until.
     * @return List of Vessel objects with Berth date from indicated startDate to
     *         indicated endDate.
     */
    @Query(value = "SELECT v.*, vh.last_bthgDt, vh.last_unbthgDt, vh.bthgDt_change_count, vh.unbthgDt_change_count, vh.first_arrival from vessel v, vessel_history vh"
            + " where v.uniqueId = vh.uniqueId and v.bthgDt >= :startDate and v.bthgDt <= :endDate", nativeQuery = true)
    List<VesselDTO> findByDate(String startDate, String endDate);

    /**
     * Custom method to find Vessel with specified uniqueId.
     * @param uniqueId ID to uniquely identify a Voyage object.
     * @return Vessel object of indicated uniqueId.
     */
    Vessel findByUniqueId(String uniqueId);

}
