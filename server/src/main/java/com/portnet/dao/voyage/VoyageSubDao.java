package com.portnet.dao.voyage;

import com.portnet.entity.voyage.VoyageSub;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Data Access Objects for "voyage_sub" table to perform various operations
 */

@Repository
public interface VoyageSubDao extends JpaRepository<VoyageSub, Integer> {
    /**
     * Additional custom method to find subscriptions by the user's ID
     * @param userId the auto-generated ID of the user
     * @return VoyageSubs array (empty list if no VoyageSub found)
     */
    List<VoyageSub> findByUserId(int userId);

    /**
     * Additional custom method to delete subscriptions by the user's ID and voyage's ID
     * @param userId the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage
     */
    @Transactional
    void deleteByUserIdAndVoyageId(int userId, String voyageId);
}
