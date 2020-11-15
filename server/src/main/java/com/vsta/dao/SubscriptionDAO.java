package com.vsta.dao;

import java.util.List;

import javax.transaction.Transactional;

import com.vsta.model.Subscription;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "subscription" table.
 * Used to perform various operations on the database
 * including retrieval and modification.
 */
@Repository
public interface SubscriptionDAO extends CrudRepository<Subscription, Integer> {
    /**
     * Custom method to find subscriptions by the user's ID
     * @param userId ID to uniquely identify a User
     * @return Subscriptions array (empty list if no matching Subscription found)
     */
    List<Subscription> findByUserId(int userId);

    /**
     * Custom method to delete subscriptions by the user's ID and voyage's ID.
     * @param userId   ID to uniquely identify a User.
     * @param voyageId ID to uniquely identify a Voyage.
     */
    @Transactional
    void deleteByUserIdAndVoyageId(int userId, String voyageId);

    /**
     * Custom method to find if Subscription is in database.
     * @param userId   ID to uniquely identify a User.
     * @param voyageId ID to uniquely identify a Voyage.
     * @return list of Subscription objects of indicated userId and voyageId.
     */
    @Query("select s from Subscription s where s.userId = :userId and s.voyageId = :voyageId")
    List<Subscription> findSubscriptionByUserIdAndVoyageId(@Param("userId") Integer userId,
                                                        @Param("voyageId") String voyageId);

    /**
     * Custom method to find users who subscribed to a voyage.
     * @param voyageId ID to uniquely identify a Voyage.
     * @return a list of user projections of indicated userId and voyageId.
     */
    @Query(value = "select u.email from user u inner join subscription v on u.id = v.user_id "
            + "where v.voyage_id = :voyageId", nativeQuery = true)
    List<UserProjection> findSubs(@Param("voyageId") String voyageId);

    /**
     * Projection helper to support the conversion of result
     * from the native query object to a temporary User entity.
     * <p>
     * Naming convention must follow exact name in the entity
     * hence the lack of camel cased letters.
     */
    interface UserProjection {
        String getemail();
    }
}
