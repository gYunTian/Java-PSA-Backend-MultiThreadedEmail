package com.vsta.dao;

import java.util.List;

import com.vsta.dto.VesselDTO;
import com.vsta.entity.Vessel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Object shared by "vessel" table and "vesselDTO"
 * Used to perform various operations
 * 
 */

@Repository
public interface VesselDao extends CrudRepository<Vessel, String> {
  
  /**
   * startDate and endDate cannot be same and endDate must minimally be 1 day later
   * @param startDate user specified Date with format YYYY-MM-DD to retrieve vessels from
   * @param endDate user specified Date with format YYYY-MM-DD to retrieve vessels to
   * @return list of Vessel objects
   */
  @Query(value = "SELECT v.*, vh.last_bthgDt, vh.last_unbthgDt, vh.bthgDt_change_count, vh.unbthgDt_change_count, vh.first_arrival from vessel v, vessel_history vh" +
  " where v.uniqueId = vh.uniqueId and v.bthgDt >= :startDate and v.bthgDt <= :endDate", nativeQuery = true)
  public List<VesselDTO> findByDate(String startDate, String endDate);
  
}
