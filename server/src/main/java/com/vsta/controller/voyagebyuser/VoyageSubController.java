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
     * Add methods
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

    @GetMapping("/voyageSubsByUserId/{userId}")
    public List<VoyageSub> findVoyageSubsByUserId(@PathVariable int userId) {
        return service.getVoyageSubByUserId(userId);
    }

    /**
     * Delete methods
     */

    @DeleteMapping("/deleteVoyageSubs")
    public ResponseEntity<String> deleteVoyageSub(@RequestBody VoyageSub voyageSub) {
        return service.deleteVoyageSub(voyageSub);
    }

}