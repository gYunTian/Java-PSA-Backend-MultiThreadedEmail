package com.vsta.service;

import com.vsta.dao.SubscriptionDAO;
import com.vsta.dto.UserDTO;
import com.vsta.model.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Subscription Service tasks that use DAO methods
 * and used for REST APIs for Subscription Object.
 */

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionDAO subscriptionDao;

    @Autowired
    private UserService userService;

    @Autowired
    private VesselService vesselService;

    // Response messages
    final String subErrorMsgPrefix = "Voyage subscription unsuccessful - ";

    final String nonExistentUserMsg = subErrorMsgPrefix + "user do not exist";
    final String nonExistentVoyageMsg = subErrorMsgPrefix + "voyage do not exist";
    final String existingSubMsg = subErrorMsgPrefix + "subscription already exist";

    final String unsubErrorMsg = "Voyage unsubscription unsuccessful - subscription does not exist";

    final String subSuccessMsg = "Voyage subscribed successfully";
    final String unSubSuccessMsg = "Voyage unsubscribed successfully";

    /**
     * Check if Subscription object can be saved in database.
     * @param subscription object to be save in database.
     * @return  ResponseEntity with an error message and
     *          400 status code if invalid, else null.
     */
    private ResponseEntity<String> invalidSubscriptionResponse(Subscription subscription) {

        int userId = subscription.getUserId();
        if (userService.getUserById(userId) == null){
            return new ResponseEntity<>(nonExistentUserMsg, HttpStatus.BAD_REQUEST);
        }
        String voyageId = subscription.getVoyageId();
        if (vesselService.getVesselByUniqueId(voyageId) == null){
            return new ResponseEntity<>(nonExistentVoyageMsg, HttpStatus.BAD_REQUEST);
        }

        List<Subscription> subscriptionList = subscriptionDao.findSubscriptionByUserIdAndVoyageId(userId, voyageId);
        if (subscriptionList.size() >= 1){
            return new ResponseEntity<>(existingSubMsg, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    /**
     * Add Subscription to database.
     * @param subscription object to be save in database.
     * @return  ResponseEntity with a status code and message
     *          indicating if subscription added successfully.
     */
    public ResponseEntity<String> saveSubscription(Subscription subscription) {

        ResponseEntity<String> invalidResponse = invalidSubscriptionResponse(subscription);
        if (invalidResponse != null) {
            return invalidResponse;
        }

        subscriptionDao.save(subscription);
        return ResponseEntity.ok(subSuccessMsg);
    }

    /**
     * Get Subscriptions with specified userId in database.
     * @param userId ID to uniquely identify a User.
     * @return List of subscription objects subscribed to by User.
     */
    public List<Subscription> getSubscriptionByUserId(int userId) {
        return subscriptionDao.findByUserId(userId);
    }

    /**
     * Remove specified subscription from database.
     * @param subscription Subscription object to be removed.
     * @return  ResponseEntity with a status code and message
     *          indicating if subscription is deleted successfully.
     */
    public ResponseEntity<String> deleteSubscription(Subscription subscription) {
        int userId = subscription.getUserId();
        String voyageId = subscription.getVoyageId();

        List<Subscription> subscriptionList = subscriptionDao.findSubscriptionByUserIdAndVoyageId(userId, voyageId);
        if (subscriptionList == null || subscriptionList.size() == 0){
            return new ResponseEntity<>(unsubErrorMsg, HttpStatus.BAD_REQUEST);
        }

        subscriptionDao.deleteByUserIdAndVoyageId(userId, voyageId);
        return ResponseEntity.ok(unSubSuccessMsg);
    }

    /**
     * Get the emails of all users subbed to a voyage/vessel.
     * @param voyageId ID to uniquely identify a Voyage.
     * @return List of email strings of users subbed to indicated voyageId;
     */
    public List<UserDTO> getSubscribers(String voyageId) {
        return subscriptionDao.findSubs(voyageId);
    }

}
