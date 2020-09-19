package com.portnet.controller.voyage;

import com.portnet.entity.voyage.VoyageFav;
import com.portnet.service.voyage.VoyageFavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for VoyageFav
 */

@RestController
public class VoyageFavController {

    @Autowired
    private VoyageFavService service;

    /**
     * Add methods
     */

    @PostMapping("/addVoyageFav")
    public void addVoyageFav(@RequestBody VoyageFav voyageFav) {
        service.saveVoyageFav(voyageFav);
    }

    @PostMapping("/addVoyageFavs")
    public void addVoyageFavs(@RequestBody List<VoyageFav> voyageFavs) {
        service.saveVoyageFavs(voyageFavs);
    }

    /**
     * Get methods
     */

    @GetMapping("/voyageFavs")
    public List<VoyageFav> findAllVoyageFavs() {
        return service.getVoyageFav();
    }

    @GetMapping("/voyageFavsByUserId/{userId}")
    public List<VoyageFav> findVoyageFavsByUserId(@PathVariable int userId) {
        return service.getVoyageFavByUserId(userId);
    }

    /**
     * Delete methods
     */

    @DeleteMapping("/deleteVoyageFav/{userId}/{voyageId}")
    public void deleteVessel(@PathVariable int userId, int voyageId) {
        service.deleteVoyageFav(userId, voyageId);
    }

}
