package com.portnet.dao.voyage;

import com.portnet.entity.voyage.Voyage;
import com.portnet.utility.VoyageId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "voyage_in" table to perform various operations
 */

@Repository
public interface VoyageInDao extends JpaRepository<Voyage, VoyageId> {
}
