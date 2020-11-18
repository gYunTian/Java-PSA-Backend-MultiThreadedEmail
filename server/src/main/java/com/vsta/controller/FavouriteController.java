package com.vsta.controller;

import com.vsta.model.Favourite;
import com.vsta.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for Favourite.
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FavouriteController {

    @Autowired
    private FavouriteService service;

    /**
     * Add voyage favorite to database.
     * @param favourite Favourite object to be added into database.
     * @return  ResponseEntity with a status code and message
     *          indicating if favourite is added successfully.
     */
    @PostMapping("/addFavourite")
    public ResponseEntity<String> addFavourite(@RequestBody Favourite favourite) {
        return service.saveFavourite(favourite);
    }

    /**
     * Get all voyage favourites by the User's ID.
     * @param userId ID to uniquely identify a User.
     * @return  List of Favourite objects of the
     *          indicated userId.
     */
    @GetMapping("/favouritesByUserId/{userId}")
    public List<Favourite> findFavouritesByUserId(@PathVariable int userId) {
        return service.getFavouriteByUserId(userId);
    }

    /**
     * Delete specified favourite from database.
     * @param favourite Favourite object requested to be removed
     *                  from database.
     * @return  ResponseEntity with a status code and message
     *          indicating if favourite is deleted successfully.
     */
    @DeleteMapping("/deleteFavourite")
    public ResponseEntity<String> deleteFavourite(@RequestBody Favourite favourite) {
        return service.deleteFavourite(favourite);
    }

}
