package com.vsta.controller;

import com.vsta.entity.Subscription;
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
     * Add method - Add voyage subscription to database
     * @param subscription subscription object to be added into database
     * @return ResponseEntity with the given status code and message indicating if subscription is added successfully
     */

    @PostMapping("/addVoyageSub")
    public ResponseEntity<String> addVoyageSub(@RequestBody Subscription subscription) {
        return service.saveVoyageSub(subscription);
    }

    // @PostMapping("/addVoyageSubs")
    // public void addVoyageSubs(@RequestBody List<Subscription> voyageSubs) {
    //     service.saveVoyageSubs(voyageSubs);
    // }

    /**
     * Get methods
     */

    // @GetMapping("/voyageSubs")
    // public List<Subscription> findAllVoyageSubs() {
    //     return service.getVoyageSub();
    // }

    /**
     * Get method - Get all voyage subscriptions by userId
     * @param userId the auto-generated ID of the user
     * @return a list of voyageSub objects
     */
    @GetMapping("/voyageSubsByUserId/{userId}")
    public List<Subscription> findVoyageSubsByUserId(@PathVariable int userId) {
        return service.getVoyageSubByUserId(userId);
    }

    /**
     * Delete method - Remove specified subscription from database
     * @param subscription subscription object requested to be remove from database
     * @return ResponseEntity with the given status code and message indicating if subscription is deleted successfully
     */

    @DeleteMapping("/deleteVoyageSubs")
    public ResponseEntity<String> deleteVoyageSub(@RequestBody Subscription subscription) {
        return service.deleteVoyageSub(subscription);
    }

}
