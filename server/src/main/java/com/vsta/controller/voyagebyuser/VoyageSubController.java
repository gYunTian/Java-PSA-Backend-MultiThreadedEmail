package com.vsta.controller.voyagebyuser;

import com.vsta.entity.voyagebyuser.VoyageSub;
import com.vsta.service.voyagebyuser.VoyageSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for VoyageSub
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VoyageSubController {

    @Autowired
    private VoyageSubService service;

    /**
     * Add method - Add voyage subscription to database
     * @param voyageSub voyageSub object to be added into database
     * @return ResponseEntity with the given status code and message indicating if voyageSub is added successfully
     */

    @PostMapping("/addVoyageSub")
    public ResponseEntity<String> addVoyageSub(@RequestBody VoyageSub voyageSub) {
        return service.saveVoyageSub(voyageSub);
    }

    // @PostMapping("/addVoyageSubs")
    // public void addVoyageSubs(@RequestBody List<VoyageSub> voyageSubs) {
    //     service.saveVoyageSubs(voyageSubs);
    // }

    /**
     * Get methods
     */

    // @GetMapping("/voyageSubs")
    // public List<VoyageSub> findAllVoyageSubs() {
    //     return service.getVoyageSub();
    // }

    /**
     * Get method - Get all voyage subscriptions by userId
     * @param userId the auto-generated ID of the user
     * @return a list of voyageSub objects
     */
    @GetMapping("/voyageSubsByUserId/{userId}")
    public List<VoyageSub> findVoyageSubsByUserId(@PathVariable int userId) {
        return service.getVoyageSubByUserId(userId);
    }

    /**
     * Delete method - Remove specified voyageSub from database
     * @param voyageSub voyageSub object requested to be remove from database
     * @return ResponseEntity with the given status code and message indicating if voyageSub is deleted successfully
     */

    @DeleteMapping("/deleteVoyageSubs")
    public ResponseEntity<String> deleteVoyageSub(@RequestBody VoyageSub voyageSub) {
        return service.deleteVoyageSub(voyageSub);
    }

}
