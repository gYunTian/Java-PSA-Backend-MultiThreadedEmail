package com.vsta.service.voyagebyuser;

import com.vsta.dao.voyagebyuser.VoyageFavDao;
import com.vsta.entity.voyagebyuser.VoyageFav;
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
     * @param voyageFav object
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
     * @param voyageFavs array
     */
    public void saveVoyageFavs(List<VoyageFav> voyageFavs) {
        voyageFavDao.saveAll(voyageFavs);
    }

    
    /**
     * Get all VoyageFavs in database
     * @return voyageFavs array
     */
    public List<VoyageFav> getVoyageFav() {
        return voyageFavDao.findAll();
    }

    /**
     * Get VoyageFavs with specified userId in database
     * @param userId the auto-generated ID of the user
     * @return voyageFav object
     */
    public List<VoyageFav> getVoyageFavByUserId(int userId) {
        return voyageFavDao.findByUserId(userId);
    }


    /**
     * Remove VoyageFav with specified userId and voyageId from database
     * @param voyageFav object
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
