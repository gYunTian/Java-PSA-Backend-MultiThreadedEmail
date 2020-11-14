package com.vsta.service;

import com.vsta.dao.FavouriteDAO;
import com.vsta.model.Favourite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * User Service tasks that use DAO methods
 * and used for REST APIs for Favourite Object.
 */

@Service
public class FavouriteService {

    @Autowired
    private FavouriteDAO favouriteDao;

    /**
     * Add Favourite to database
     * @param favourite Favourite object to be saved in database
     * @return  ResponseEntity with the given status code and message
     *          indicating if favourite added successfully
     */
    public ResponseEntity<String> saveFavourite(Favourite favourite) {
        int userId = favourite.getUserId();
        String voyageId = favourite.getVoyageId();
        List<Favourite> favouriteList = favouriteDao.findFavouriteByUserIdAndVoyageId(userId, voyageId);
        if (favouriteList.size() >= 1){
            return new ResponseEntity<>(
                    "Voyage favourite unsuccessful as it already exist",
                    HttpStatus.BAD_REQUEST);
        }
        favouriteDao.save(favourite);
        return ResponseEntity.ok("Voyage favourited successful");
    }

    /**
     * Get Favourites with specified userId in database
     * @param userId Auto-generated ID of User
     * @return List of favourite objects
     */
    public List<Favourite> getFavouriteByUserId(int userId) {
        return favouriteDao.findByUserId(userId);
    }


    /**
     * Remove specified favourite from database
     * @param favourite Favourite object to be removed
     * @return  ResponseEntity with the given status code and message
     *          indicating if favourite is deleted successfully
     */
    public ResponseEntity<String> deleteFavourite(Favourite favourite) {
        int userId = favourite.getUserId();
        String voyageId = favourite.getVoyageId();

        List<Favourite> favouriteList = favouriteDao.findFavouriteByUserIdAndVoyageId(userId, voyageId);
        if (favouriteList.size() == 0){
            return new ResponseEntity<>(
                    "Voyage unfavourite unsuccessful as it does not exist",
                    HttpStatus.BAD_REQUEST);
        }
        favouriteDao.deleteByUserIdAndVoyageId(userId, voyageId);
        return ResponseEntity.ok("Voyage unfavourited successfully");
    }

}
