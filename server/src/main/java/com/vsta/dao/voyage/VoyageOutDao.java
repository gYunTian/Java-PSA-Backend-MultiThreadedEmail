package com.vsta.dao.voyage;

import com.vsta.entity.voyage.VoyageOut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "voyage_out" table to perform various operations
 */

@Repository
public interface VoyageOutDao extends JpaRepository<VoyageOut, Integer> {
}
