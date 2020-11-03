package com.portnet.dao.storage;

import java.util.List;

import com.portnet.entity.storage.Vessel;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "vessel" table to perform various operations
 */
@Repository
public interface VesselDao extends CrudRepository<Vessel, String> {

  /**
   * startDate and endDate cannot be same and endDate must minimally be 1 day later
   * @param startDate
   * @param endDate
   * @return
   */
  @Query(value = "SELECT v.*, vh.last_bthgDt, vh.last_unbthgDt, vh.bthgDt_change_count, vh.unbthgDt_change_count, vh.first_arrival from vessel v, vessel_history vh" +
  " where v.uniqueId = vh.uniqueId and v.bthgDt >= :startDate and v.bthgDt <= :endDate", nativeQuery = true)
  public List<VesselProjection> findByDate(String startDate, String endDate);

  /**
   * Projection helper to support the conversion 
   * of result from native query object to a temporary Vessel entity
   * 
   * Naming convention must follow exact name in the entity
   * hence the lack of camel cased letters
   */
  public static interface VesselProjection {
    String getuniqueId();
    String getimoN();
    String getfullVslM();
    String getabbrVslM();
    String getfullInVoyN();
    String getinVoyN();
    String getoutVoyN();
    String getfullOutVoyN();
    String getshiftSeqN();
    String getbthgDt();
    String getunbthgDt();
    String getberthN();
    String getstatus();
    String getabbrTerminalM();
    String getlast_bthgDt();
    String getlast_unbthgDt();
    int getbthgDt_change_count();
    int getunbthgDt_change_count();
    String getfirst_arrival();
 }
}
