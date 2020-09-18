package com.portnet.service.voyage;

import com.portnet.dao.voyage.VoyageFavDao;
import com.portnet.entity.voyage.Voyage;
import com.portnet.entity.voyage.VoyageFav;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void saveVoyageFav(VoyageFav voyageFav) {
        voyageFavDao.save(voyageFav);
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
     * @param userId the auto-generated ID of the user
     * @param voyageId the auto-generated ID of the voyage, identified by vesselName and voyageNum
     */
    public void deleteVoyageFav(int userId, int voyageId) {
        voyageFavDao.deleteByUserIdAndVoyageId(userId, voyageId);
    }

}
