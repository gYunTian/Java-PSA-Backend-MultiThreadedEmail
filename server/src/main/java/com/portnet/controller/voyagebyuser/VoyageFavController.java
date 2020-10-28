package com.portnet.controller.voyagebyuser;

import com.portnet.entity.voyagebyuser.VoyageFav;
import com.portnet.service.voyagebyuser.VoyageFavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for VoyageFav
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VoyageFavController {

    @Autowired
    private VoyageFavService service;

    /**
     * Add methods
     */

    @PostMapping("/addVoyageFav")
    public ResponseEntity<String> addVoyageFav(@RequestBody VoyageFav voyageFav) {
        return service.saveVoyageFav(voyageFav);
    }

    // @PostMapping("/addVoyageFavs")
    // public void addVoyageFavs(@RequestBody List<VoyageFav> voyageFavs) {
    //     service.saveVoyageFavs(voyageFavs);
    // }

    /**
     * Get methods
     */

    // @GetMapping("/voyageFavs")
    // public List<VoyageFav> findAllVoyageFavs() {
    //     return service.getVoyageFav();
    // }

    @GetMapping("/voyageFavsByUserId/{userId}")
    public List<VoyageFav> findVoyageFavsByUserId(@PathVariable int userId) {
        return service.getVoyageFavByUserId(userId);
    }

    /**
     * Delete methods
     */

    @DeleteMapping("/deleteVoyageFav/{userId}/{voyageId}")
    public ResponseEntity<String> deleteVoyageFav(@PathVariable int userId,@PathVariable String voyageId) {
        return service.deleteVoyageFav(userId, voyageId);
    }

}
