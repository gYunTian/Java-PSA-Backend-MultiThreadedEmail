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
    final String unSubErrorMsgPrefix = "Voyage unfavourite unsuccessful - ";

    final String subNonExistentUserMsg = subErrorMsgPrefix + "user does not exist";
    final String subNonExistentVoyageMsg = subErrorMsgPrefix + "voyage does not exist";
    final String subExistingSubMsg = subErrorMsgPrefix + "favourite already exists";

    final String unSubNonExistentFavouriteMsg = unSubErrorMsgPrefix + "favourite does not exist";

    final String subSuccessMsg = "Voyage favourited successfully";
    final String unSubSuccessMsg = "Voyage unfavourited successfully";

    /**
     * Check if Favourite object can be saved in database.
     * @param favourite object to be save in database.
     * @return  ResponseEntity with an error message and
     *          400 status code if invalid, else null
     */
    public ResponseEntity<String> invalidFavouriteResponse(Favourite favourite) {

        int userId = favourite.getUserId();
        if (userService.getUserById(userId) == null){
            return new ResponseEntity<>(subNonExistentUserMsg, HttpStatus.BAD_REQUEST);
        }
        String voyageId = favourite.getVoyageId();
        if (vesselService.getVesselByUniqueId(voyageId) == null){
            return new ResponseEntity<>(subNonExistentVoyageMsg, HttpStatus.BAD_REQUEST);
        }

        List<Favourite> favouriteList = favouriteDao.findFavouriteByUserIdAndVoyageId(userId, voyageId);
        if (favouriteList.size() >= 1){
            return new ResponseEntity<>(subExistingSubMsg, HttpStatus.BAD_REQUEST);
        }

        return null;
    }

    /**
     * Add Favourite to database
     * @param favourite Favourite object to be saved in database
     * @return  ResponseEntity with a status code and message
     *          indicating if favourite added successfully
     */
    public ResponseEntity<String> saveFavourite(Favourite favourite) {

        ResponseEntity<String> invalidResponse = invalidFavouriteResponse(favourite);
        if (invalidResponse != null) {
            return invalidResponse;
        }

        favouriteDao.save(favourite);
        return ResponseEntity.ok(subSuccessMsg);
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
     * @return  ResponseEntity with a status code and message
     *          indicating if favourite is deleted successfully
     */
    public ResponseEntity<String> deleteFavourite(Favourite favourite) {
        int userId = favourite.getUserId();
        String voyageId = favourite.getVoyageId();

        List<Favourite> favouriteList = favouriteDao.findFavouriteByUserIdAndVoyageId(userId, voyageId);
        if (favouriteList.size() == 0){
            return new ResponseEntity<>(unSubNonExistentFavouriteMsg, HttpStatus.BAD_REQUEST);
        }
        favouriteDao.deleteByUserIdAndVoyageId(userId, voyageId);
        return ResponseEntity.ok(unSubSuccessMsg);
    }

}
