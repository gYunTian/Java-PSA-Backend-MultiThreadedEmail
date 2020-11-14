package com.vsta.controller;

import com.vsta.model.Subscription;
import com.vsta.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for Subscription
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService service;

    /**
     * Add voyage subscription to database
     * @param subscription Subscription object to be added into database
     * @return  ResponseEntity with the given status code and message
     *          indicating if subscription is added successfully
     */
    @PostMapping("/addSubscription")
    public ResponseEntity<String> addSubscription(@RequestBody Subscription subscription) {
        return service.saveSubscription(subscription);
    }

    /**
     * Get all voyage subscriptions by userId
     * @param userId the auto-generated ID of the user
     * @return  List of subscription objects
     */
    @GetMapping("/subscriptionsByUserId/{userId}")
    public List<Subscription> findSubscriptionsByUserId(@PathVariable int userId) {
        return service.getSubscriptionByUserId(userId);
    }

    /**
     * Delete specified subscription from database
     * @param subscription subscription object requested to be remove from database
     * @return  ResponseEntity with the given status code and message
     *          indicating if subscription is deleted successfully
     */
    @DeleteMapping("/deleteSubscriptions")
    public ResponseEntity<String> deleteSubscription(@RequestBody Subscription subscription) {
        return service.deleteSubscription(subscription);
    }

}
