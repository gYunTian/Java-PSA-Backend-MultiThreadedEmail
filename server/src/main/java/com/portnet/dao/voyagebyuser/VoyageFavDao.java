package com.portnet.dao.voyagebyuser;

import com.portnet.entity.voyagebyuser.VoyageFav;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Data Access Objects for "voyage_fav" table to perform various operations
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
     * @param voyageId the unique ID of the voyage
     */
    @Transactional
    void deleteByUserIdAndVoyageId(int userId, String voyageId);

    /**
     * Additional custom method to find if VoyageFav is in database
     * @param userId the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage
     */

    @Query("select f from VoyageFav f where f.userId = :userId and f.voyageId = :voyageId")
    List<VoyageFav> findVoyageFavByUserIdAndVoyageId(@Param("userId") Integer userId,@Param("voyageId") String voyageId);
}