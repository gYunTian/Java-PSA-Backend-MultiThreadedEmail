package com.vsta.dao;

import com.vsta.model.Subscription;
import com.vsta.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

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
     * @return List of user of indicated voyageId.
     */
    @Query(value = "select u from user u inner join subscription v on u.id = v.user_id "
            + "where v.voyage_id = :voyageId", nativeQuery = true)
    List<User> findSubs(@Param("voyageId") String voyageId);

}
