package com.vsta.service;

import com.vsta.dao.VoyageFavDao;
import com.vsta.entity.VoyageFav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageFavService {

    @Autowired
    private VoyageFavDao voyageFavDao;

    /**
     * Add VoyageFav to database
     * @param voyageFav object to be save in database
     * @return ResponseEntity with the given status code and message indicating if voyageFav added successfully
     */
    public ResponseEntity<String> saveVoyageFav(VoyageFav voyageFav) {
        int userId = voyageFav.getUserId();
        String voyageId = voyageFav.getVoyageId();
        List<VoyageFav> voyageFavList = voyageFavDao.findVoyageFavByUserIdAndVoyageId(userId, voyageId);
        if (voyageFavList.size() >= 1){
            return new ResponseEntity<>(
                    "Voyage favourite unsuccessful as it already exist",
                    HttpStatus.BAD_REQUEST);
        }
        voyageFavDao.save(voyageFav);
        return ResponseEntity.ok("Voyage favourited successful");
    }

    /**
     * Add VoyageFavs in array to database
     * @param voyageFavs a list of voyageFav objects
     */
    public void saveVoyageFavs(List<VoyageFav> voyageFavs) {
        voyageFavDao.saveAll(voyageFavs);
    }

    
    /**
     * Get a list of VoyageFavs from database
     * @return a list of voyageFav objects
     */
    public List<VoyageFav> getVoyageFav() {
        return voyageFavDao.findAll();
    }

    /**
     * Get VoyageFavs with specified userId in database
     * @param userId the auto-generated ID of the user
     * @return a list of voyageFav objects
     */
    public List<VoyageFav> getVoyageFavByUserId(int userId) {
        return voyageFavDao.findByUserId(userId);
    }


    /**
     * Remove specified voyageFav from database
     * @param voyageFav object that to be removed
     * @return ResponseEntity with the given status code and message indicating if voyageFav is deleted successfully
     */
    public ResponseEntity<String> deleteVoyageFav(VoyageFav voyageFav) {
        int userId = voyageFav.getUserId();
        String voyageId = voyageFav.getVoyageId();

        List<VoyageFav> voyageFavList = voyageFavDao.findVoyageFavByUserIdAndVoyageId(userId, voyageId);
        if (voyageFavList.size() == 0){
            return new ResponseEntity<>(
                    "Voyage unfavourite unsuccessful as it does not exist",
                    HttpStatus.BAD_REQUEST);
        }
        voyageFavDao.deleteByUserIdAndVoyageId(userId, voyageId);
        return ResponseEntity.ok("Voyage unfavourited successfully");
    }

}
