package com.portnet.service.voyage;

import com.portnet.dao.voyage.VoyageSubDao;
import com.portnet.entity.voyage.VoyageSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageSubService {

    @Autowired
    private VoyageSubDao voyageSubDao;

    /**
     * Add VoyageSub to database
     * @param voyageSub object
     */
    public ResponseEntity<String> saveVoyageSub(VoyageSub voyageSub) {
        int userId = voyageSub.getUserId();
        String voyageId = voyageSub.getVoyageId();
        List<VoyageSub> voyageSubList = voyageSubDao.findVoyageSubByUserIdAndVoyageId(userId, voyageId);
        if (voyageSubList.size() >= 1){
            return new ResponseEntity<>(
                    "voyageSub not added - voyageSub already exist",
                    HttpStatus.BAD_REQUEST);
        }
        voyageSubDao.save(voyageSub);
        return ResponseEntity.ok("Voyage Subscription added successful");
    }

    /**
     * Add VoyageSubs in array to database
     * @param voyageSubs array
     */
    public void saveVoyageSubs(List<VoyageSub> voyageSubs) {
        voyageSubDao.saveAll(voyageSubs);
    }

    
    /**
     * Get all VoyageSubs in database
     * @return voyageSubs array
     */
    public List<VoyageSub> getVoyageSub() {
        return voyageSubDao.findAll();
    }

    /**
     * Get VoyageSubs with specified userId in database
     * @param userId the auto-generated ID of the user
     * @return voyageSub object
     */
    public List<VoyageSub> getVoyageSubByUserId(int userId) {
        return voyageSubDao.findByUserId(userId);
    }


    /**
     * Remove VoyageSub with specified userId and voyageId from database
     * @param userId the auto-generated ID of the user
     * @param voyageId the unique ID of the voyage
     */
    public ResponseEntity<String> deleteVoyageSub(int userId, String voyageId) {
        List<VoyageSub> voyageSubList = voyageSubDao.findVoyageSubByUserIdAndVoyageId(userId, voyageId);
        if (voyageSubList.size() == 0){
            return new ResponseEntity<>(
                    "Voyage unsubscription unsuccessful - subscription does not exist",
                    HttpStatus.BAD_REQUEST);
        }
        voyageSubDao.deleteByUserIdAndVoyageId(userId, voyageId);
        return ResponseEntity.ok("Voyage unsubscription successful");
    }

}
