package com.vsta.controller;

import com.vsta.model.Favourite;
import com.vsta.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for
 * Favourite
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FavouriteController {

    @Autowired
    private FavouriteService service;

    /**
     * Add Add voyage favorite to database.
     * @param favourite Favourite object to be added into database.
     * @return  ResponseEntity with the given status code and message
     *          indicating if favourite is added successfully.
     */
    @PostMapping("/addFavourite")
    public ResponseEntity<String> addFavourite(@RequestBody Favourite favourite) {
        return service.saveFavourite(favourite);
    }

    /**
     * Get all voyage favourites by userId.
     * @param userId ID to uniquely identify a User.
     * @return list of Favourite objects of indicated userId.
     */
    @GetMapping("/favouritesByUserId/{userId}")
    public List<Favourite> findFavouritesByUserId(@PathVariable int userId) {
        return service.getFavouriteByUserId(userId);
    }

    /**
     * Delete specified favourite from database
     * @param favourite Favourite object requested to be remove from database
     * @return  ResponseEntity with the given status code and message
     *          indicating if favourite is deleted successfully
     */
    @DeleteMapping("/deleteFavourite")
    public ResponseEntity<String> deleteFavourite(@RequestBody Favourite favourite) {
        return service.deleteFavourite(favourite);
    }

}
