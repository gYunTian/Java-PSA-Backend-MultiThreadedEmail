package com.portnet.dao.voyage;

import com.portnet.entity.voyage.VoyageFav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Objects for "favourites" table to perform various operations
 */

@Repository
public interface VoyageFavDao extends JpaRepository<VoyageFav, Integer> {
    /**
     * Additional custom method to find favourites by the user's ID
     * @param userId the auto-generated ID of the user
     * @return voyageFavs array (empty list if no voyageFav found)
     */
    List<VoyageFav> findByUserId(int userId);

    /**
     * Additional custom method to delete favourites by the user's ID and voyage's ID
     * @param userId the auto-generated ID of the user
     * @param voyageId the auto-generated ID of the voyage, identified by vesselName and voyageNum
     */
    void deleteByUserIdAndVoyageId(int userId, int voyageId);
}
