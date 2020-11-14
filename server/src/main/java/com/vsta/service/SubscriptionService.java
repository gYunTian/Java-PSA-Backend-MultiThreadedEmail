package com.vsta.service;

import com.vsta.dao.SubscriptionDAO;
import com.vsta.dao.SubscriptionDAO.UserProjection;
import com.vsta.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User Service tasks that use DAO methods
 * and used for REST APIs for Subscription Object.
 */

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionDAO subscriptionDao;

    /**
     * Add Subscription to database
     * @param subscription object to be save in database
     * @return  ResponseEntity with the given status code and message
     *          indicating if subscription added successfully
     */
    public ResponseEntity<String> saveSubscription(Subscription subscription) {
        int userId = subscription.getUserId();
        String voyageId = subscription.getVoyageId();
        List<Subscription> subscriptionList = subscriptionDao.findSubscriptionByUserIdAndVoyageId(userId, voyageId);
        if (subscriptionList.size() >= 1){
            return new ResponseEntity<>(
                    "Voyage subscription unsuccessful as it already exist",
                    HttpStatus.BAD_REQUEST);
        }
        subscriptionDao.save(subscription);
        return ResponseEntity.ok("Voyage subscribed successful");
    }

    /**
     * Get Subscriptions with specified userId in database
     * @param userId the auto-generated ID of the user
     * @return List of subscription objects subscribed to by User
     */
    public List<Subscription> getSubscriptionByUserId(int userId) {
        return subscriptionDao.findByUserId(userId);
    }

    /**
     * Remove specified subscription from database
     * @param subscription Subscription object to be removed
     * @return  ResponseEntity with the given status code and message
     *          indicating if subscription is deleted successfully
     */
    public ResponseEntity<String> deleteSubscription(Subscription subscription) {
        int userId = subscription.getUserId();
        String voyageId = subscription.getVoyageId();

        List<Subscription> subscriptionList = subscriptionDao.findSubscriptionByUserIdAndVoyageId(userId, voyageId);
        if (subscriptionList.size() == 0){
            return new ResponseEntity<>(
                    "Voyage unsubscription unsuccessful - subscription does not exist",
                    HttpStatus.BAD_REQUEST);
        }
        subscriptionDao.deleteByUserIdAndVoyageId(userId, voyageId);
        return ResponseEntity.ok("Voyage unsubscription successful");
    }

    /**
     * Get the emails of all users subbed to a voyage/vessel
     * @param voyageId Unique ID of the voyage
     * @return List of email strings
     */ 
    public List<String> getSubs(String voyageId) {
        List<String> emails = new ArrayList<>();
        List<UserProjection> users = subscriptionDao.findSubs(voyageId);
        
        for (UserProjection user : users) {
            emails.add(user.getemail());
        }
        
        return emails;
    }

}
