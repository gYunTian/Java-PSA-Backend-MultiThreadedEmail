package com.portnet.controller.voyagebyuser;

import com.portnet.entity.voyagebyuser.VoyageSub;
import com.portnet.service.voyagebyuser.VoyageSubService;
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
    public ResponseEntity<String> deleteVoyageSub(@PathVariable int userId,@PathVariable String voyageId) {
        return service.deleteVoyageSub(userId, voyageId);
    }

}
