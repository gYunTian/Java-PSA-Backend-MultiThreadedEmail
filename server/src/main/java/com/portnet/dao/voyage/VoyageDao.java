package com.portnet.dao.voyage;

import com.portnet.entity.voyage.Voyage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "voyage" table to perform various operations
 */

@Repository
public interface VoyageDao extends JpaRepository<Voyage, Integer> {
}
