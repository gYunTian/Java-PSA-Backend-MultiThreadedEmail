package com.vsta.controller;

import com.vsta.entity.Favourite;
import com.vsta.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for Favourite
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FavouriteController {

    @Autowired
    private FavouriteService service;

    /**
     * Add method - Add voyage favorite to database
     * @param favourite favourite object to be added into database
     * @return ResponseEntity with the given status code and message indicating if favourite is added successfully
     */

    @PostMapping("/addVoyageFav")
    public ResponseEntity<String> addVoyageFav(@RequestBody Favourite favourite) {
        return service.saveVoyageFav(favourite);
    }

    // @PostMapping("/addVoyageFavs")
    // public void addVoyageFavs(@RequestBody List<Favourite> voyageFavs) {
    //     service.saveVoyageFavs(voyageFavs);
    // }

    /**
     * Get method
     */

    // @GetMapping("/voyageFavs")
    // public List<Favourite> findAllVoyageFavs() {
    //     return service.getVoyageFav();
    // }

    /**
     * Get method - Get all voyage favourites by userId
     * @param userId the auto-generated ID of the user
     * @return a list of voyageFav objects
     */
    @GetMapping("/voyageFavsByUserId/{userId}")
    public List<Favourite> findVoyageFavsByUserId(@PathVariable int userId) {
        return service.getVoyageFavByUserId(userId);
    }

    /**
     * Delete method - Remove specified favourite from database
     * @param favourite favourite object requested to be remove from database
     * @return ResponseEntity with the given status code and message indicating if favourite is deleted successfully
     */

    @DeleteMapping("/deleteVoyageFav")
    public ResponseEntity<String> deleteVoyageFav(@RequestBody Favourite favourite) {
        return service.deleteVoyageFav(favourite);
    }

}
