package com.vsta.dao;

import java.util.List;

import javax.transaction.Transactional;

import com.vsta.entity.VoyageSub;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "voyage_sub" table to perform various operations
 */
@Repository
public interface VoyageSubDao extends CrudRepository<VoyageSub, Integer> {
    /**
     * Additional custom method to find subscriptions by the user's ID
     * 
     * @param userId the auto-generated ID of the user
     * @return VoyageSubs array (empty list if no VoyageSub found)
     */
    List<VoyageSub> findByUserId(int userId);

    /**
     * Additional custom method to delete subscriptions by the user's ID and
     * voyage's ID
     * 
     * @param userId   the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage
     */
    @Transactional
    void deleteByUserIdAndVoyageId(int userId, String voyageId);

    /**
     * Additional custom method to find if VoyageSub is in database
     * 
     * @param userId   the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage
     * @return list of voyageSub objects
     */
    @Query("select s from VoyageSub s where s.userId = :userId and s.voyageId = :voyageId")
    List<VoyageSub> findVoyageSubByUserIdAndVoyageId(@Param("userId") Integer userId,
            @Param("voyageId") String voyageId);

    /**
     * Find users who subscribed to a voyage
     * @param voyageId the unique ID of the voyage
     * @return a list of user projections
     */
    @Query(value = "select u.email from user u inner join voyage_sub v on u.id = v.user_id "
            + "where v.voyage_id = :voyageId", nativeQuery = true)
    List<UserProjection> findSubs(@Param("voyageId") String voyageId);

    /**
     * Projection helper to support the conversion of result from the native query
     * object to a temporary User entity
     * 
     * Naming convention must follow exact name in the entity hence the lack of
     * camel cased letters
     */
    public static interface UserProjection {
        String getemail();
    }
}
