package com.vsta.service;

import com.vsta.dao.FavouriteDAO;
import com.vsta.model.Favourite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Favourite Service tasks that use DAO methods
 * and used for REST APIs for Favourite Object.
 */

@Service
public class FavouriteService {

    @Autowired
    private FavouriteDAO favouriteDao;

    @Autowired
    private UserService userService;

    @Autowired
    private VesselService vesselService;

    final String subErrorMsgPrefix = "Voyage favourite unsuccessful - ";

    final String nonExistUserMsg = subErrorMsgPrefix + "user do not exist";
    final String nonExistVoyageMsg = subErrorMsgPrefix + "voyage do not exist";
    final String existingSubMsg = subErrorMsgPrefix + "favourite already exist";

    final String unSubErrorMsgPrefix = "Voyage unfavourite unsuccessful - favourite does not exist";

    /**
     * Add Favourite to database
     * @param favourite Favourite object to be saved in database
     * @return  ResponseEntity with the given status code and message
     *          indicating if favourite added successfully
     */
    public ResponseEntity<String> saveFavourite(Favourite favourite) {
        int userId = favourite.getUserId();
        if (userService.getUserById(userId) == null){
            return new ResponseEntity<>(nonExistUserMsg, HttpStatus.BAD_REQUEST);
        }

        String voyageId = favourite.getVoyageId();
        if (vesselService.getVesselByUniqueId(voyageId) == null){
            return new ResponseEntity<>(nonExistVoyageMsg, HttpStatus.BAD_REQUEST);
        }

        List<Favourite> favouriteList = favouriteDao.findFavouriteByUserIdAndVoyageId(userId, voyageId);
        if (favouriteList.size() >= 1){
            return new ResponseEntity<>(existingSubMsg, HttpStatus.BAD_REQUEST);
        }

        favouriteDao.save(favourite);
        return ResponseEntity.ok("Voyage favourited successful");
    }

    /**
     * Get Favourites with specified userId in database
     * @param userId ID to uniquely identify a User.
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
            return new ResponseEntity<>(unSubErrorMsgPrefix, HttpStatus.BAD_REQUEST);
        }
        favouriteDao.deleteByUserIdAndVoyageId(userId, voyageId);
        return ResponseEntity.ok("Voyage unfavourited successfully");
    }

}
