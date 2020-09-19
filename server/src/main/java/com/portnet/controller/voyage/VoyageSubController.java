package com.portnet.controller.voyage;

import com.portnet.entity.voyage.VoyageSub;
import com.portnet.service.voyage.VoyageSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for VoyageSub
 */

@RestController
public class VoyageSubController {

    @Autowired
    private VoyageSubService service;

    /**
     * Add methods
     */

    @PostMapping("/addVoyageSub")
    public void addVoyageSub(@RequestBody VoyageSub voyageSub) {
        service.saveVoyageSub(voyageSub);
    }

    @PostMapping("/addVoyageSubs")
    public void addVoyageSubs(@RequestBody List<VoyageSub> voyageSubs) {
        service.saveVoyageSubs(voyageSubs);
    }

    /**
     * Get methods
     */

    @GetMapping("/voyageSubs")
    public List<VoyageSub> findAllVoyageSubs() {
        return service.getVoyageSub();
    }

    @GetMapping("/voyageSubsByUserId/{userId}")
    public List<VoyageSub> findVoyageSubsByUserId(@PathVariable int userId) {
        return service.getVoyageSubByUserId(userId);
    }

    /**
     * Delete methods
     */

    @DeleteMapping("/deleteVoyageSubs/{userId}/{voyageId}")
    public void deleteVessel(@PathVariable int userId, int voyageId) {
        service.deleteVoyageSub(userId, voyageId);
    }

}
