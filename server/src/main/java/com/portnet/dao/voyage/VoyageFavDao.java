package com.portnet.dao.voyage;

import com.portnet.entity.voyage.VoyageFav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "favourites" table to perform various operations
 */

@Repository
public interface VoyageFavDao extends JpaRepository<VoyageFav, Integer> {
}
