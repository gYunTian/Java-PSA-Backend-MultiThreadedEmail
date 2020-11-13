package com.vsta.controller.voyagebyuser;

import com.vsta.entity.voyagebyuser.VoyageFav;
import com.vsta.service.voyagebyuser.VoyageFavService;
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
     * Add method - Add voyage favorite to database
     * @param voyageFav voyageFav object to be added into database
     * @return ResponseEntity with the given status code and message indicating if voyageFav is added successfully
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
     * Get method
     */

    // @GetMapping("/voyageFavs")
    // public List<VoyageFav> findAllVoyageFavs() {
    //     return service.getVoyageFav();
    // }

    /**
     * Get method - Get all voyage favourites by userId
     * @param userId the auto-generated ID of the user
     * @return a list of voyageFav objects
     */
    @GetMapping("/voyageFavsByUserId/{userId}")
    public List<VoyageFav> findVoyageFavsByUserId(@PathVariable int userId) {
        return service.getVoyageFavByUserId(userId);
    }

    /**
     * Delete method - Remove specified voyageFav from database
     * @param voyageFav voyageFav object requested to be remove from database
     * @return ResponseEntity with the given status code and message indicating if voyageFav is deleted successfully
     */

    @DeleteMapping("/deleteVoyageFav")
    public ResponseEntity<String> deleteVoyageFav(@RequestBody VoyageFav voyageFav) {
        return service.deleteVoyageFav(voyageFav);
    }

}
