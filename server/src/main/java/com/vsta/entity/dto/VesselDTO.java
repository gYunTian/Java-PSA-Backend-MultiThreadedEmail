
package com.vsta.entity.dto;

/**
 * DTO Projection helper to support the conversion 
 * of result from native query object to a temporary Vessel entity
 * Reference: https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#projections
 */

public interface VesselDTO {
    
    String getUniqueId();
    String getImoN();
    String getFullVslM();
    String getAbbrVslM();
    String getFullInVoyN();
    String getInVoyN();
    String getOutVoyN();
    String getFullOutVoyN();
    String getShiftSeqN();
    String getBthgDt();
    String getUnbthgDt();
    String getBerthN();
    String getStatus();
    String getAbbrTerminalM();
    String getLast_bthgDt();
    String getLast_unbthgDt();
    int getBthgDt_change_count();
    int getUnbthgDt_change_count();
    String getFirst_arrival();

}
