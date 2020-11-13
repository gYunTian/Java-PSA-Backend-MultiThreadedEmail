package com.vsta.dao.voyage;

import com.vsta.entity.voyage.VoyageIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "voyage_in" table to perform various operations
 */

@Repository
public interface VoyageInDao extends JpaRepository<VoyageIn, Integer> {
}
