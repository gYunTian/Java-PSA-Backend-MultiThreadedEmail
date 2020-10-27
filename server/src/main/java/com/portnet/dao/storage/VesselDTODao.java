package com.portnet.dao.storage;

import com.portnet.entity.dto.VesselDTO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "vessel" table to perform various operations
 */

@Repository
public interface VesselDTODao extends CrudRepository<VesselDTO, String> {
}
