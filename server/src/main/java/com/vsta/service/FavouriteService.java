package com.vsta.service;

import com.vsta.dao.FavouriteDao;
import com.vsta.entity.Favourite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavouriteService {

    @Autowired
    private FavouriteDao favouriteDao;

    /**
     * Add Favourite to database
     * @param favourite object to be save in database
     * @return ResponseEntity with the given status code and message indicating if favourite added successfully
     */
    public ResponseEntity<String> saveVoyageFav(Favourite favourite) {
        int userId = favourite.getUserId();
        String voyageId = favourite.getVoyageId();
        List<Favourite> favouriteList = favouriteDao.findVoyageFavByUserIdAndVoyageId(userId, voyageId);
        if (favouriteList.size() >= 1){
            return new ResponseEntity<>(
                    "Voyage favourite unsuccessful as it already exist",
                    HttpStatus.BAD_REQUEST);
        }
        favouriteDao.save(favourite);
        return ResponseEntity.ok("Voyage favourited successful");
    }

    /**
     * Add VoyageFavs in array to database
     * @param favourites a list of voyageFav objects
     */
    public void saveVoyageFavs(List<Favourite> favourites) {
        favouriteDao.saveAll(favourites);
    }

    
    /**
     * Get a list of VoyageFavs from database
     * @return a list of voyageFav objects
     */
    public List<Favourite> getVoyageFav() {
        return favouriteDao.findAll();
    }

    /**
     * Get VoyageFavs with specified userId in database
     * @param userId the auto-generated ID of the user
     * @return a list of voyageFav objects
     */
    public List<Favourite> getVoyageFavByUserId(int userId) {
        return favouriteDao.findByUserId(userId);
    }


    /**
     * Remove specified favourite from database
     * @param favourite object that to be removed
     * @return ResponseEntity with the given status code and message indicating if favourite is deleted successfully
     */
    public ResponseEntity<String> deleteVoyageFav(Favourite favourite) {
        int userId = favourite.getUserId();
        String voyageId = favourite.getVoyageId();

        List<Favourite> favouriteList = favouriteDao.findVoyageFavByUserIdAndVoyageId(userId, voyageId);
        if (favouriteList.size() == 0){
            return new ResponseEntity<>(
                    "Voyage unfavourite unsuccessful as it does not exist",
                    HttpStatus.BAD_REQUEST);
        }
        favouriteDao.deleteByUserIdAndVoyageId(userId, voyageId);
        return ResponseEntity.ok("Voyage unfavourited successfully");
    }

}
