package com.portnet.dao.voyage;

import com.portnet.entity.voyage.VoyageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Objects for "voyageId" table to perform various operations
 */

@Repository
public interface VoyageIdDao extends JpaRepository<VoyageId, Integer> {

    /**
     * Returns the voyageIds whose vesselName is given
     * as a method parameter. If no voyageIds is found, this
     * method returns an empty list.
     */
    List<VoyageId> findByVesselName(String vesselName);

    VoyageId findByVesselNameAndVoyageNum(String vesselName, String voyageNumber);

}
