package com.vsta.service.voyagebyuser;

import com.vsta.dao.voyagebyuser.VoyageSubDao;
import com.vsta.dao.voyagebyuser.VoyageSubDao.UserProjection;
import com.vsta.entity.voyagebyuser.VoyageSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoyageSubService {

    @Autowired
    private VoyageSubDao voyageSubDao;

    /**
     * Add VoyageSub to database
     * @param voyageSub object to be save in database
     * @return ResponseEntity with the given status code and message indicating if voyageSub added successfully
     */
    public ResponseEntity<String> saveVoyageSub(VoyageSub voyageSub) {
        int userId = voyageSub.getUserId();
        String voyageId = voyageSub.getVoyageId();
        List<VoyageSub> voyageSubList = voyageSubDao.findVoyageSubByUserIdAndVoyageId(userId, voyageId);
        if (voyageSubList.size() >= 1){
            return new ResponseEntity<>(
                    "Voyage subscription unsuccessful as it already exist",
                    HttpStatus.BAD_REQUEST);
        }
        voyageSubDao.save(voyageSub);
        return ResponseEntity.ok("Voyage subscribed successful");
    }

    /**
     * Add VoyageSubs in array to database
     * @param voyageSubs a list of voyageSub objects
     */
    public void saveVoyageSubs(List<VoyageSub> voyageSubs) {
        voyageSubDao.saveAll(voyageSubs);
    }

    
    /**
     * Get a list of VoyageSubs in database
     * @return a list of voyageSub objects
     */
    public List<VoyageSub> getVoyageSub() {
        return (List<VoyageSub>) voyageSubDao.findAll();
    }

    /**
     * Get VoyageSubs with specified userId in database
     * @param userId the auto-generated ID of the user
     * @return a list of voyageSub objects
     */
    public List<VoyageSub> getVoyageSubByUserId(int userId) {
        return voyageSubDao.findByUserId(userId);
    }


    /**
     * Remove specified voyageSub from database
     * @param voyageSub object that to be removed
     * @return ResponseEntity with the given status code and message indicating if voyageSub is deleted successfully
     */
    public ResponseEntity<String> deleteVoyageSub(VoyageSub voyageSub) {
        int userId = voyageSub.getUserId();
        String voyageId = voyageSub.getVoyageId();

        List<VoyageSub> voyageSubList = voyageSubDao.findVoyageSubByUserIdAndVoyageId(userId, voyageId);
        if (voyageSubList.size() == 0){
            return new ResponseEntity<>(
                    "Voyage unsubscription unsuccessful - subscription does not exist",
                    HttpStatus.BAD_REQUEST);
        }
        voyageSubDao.deleteByUserIdAndVoyageId(userId, voyageId);
        return ResponseEntity.ok("Voyage unsubscription successful");
    }

    /**
     * Get the emails of all users subbed to a voyage/vessel
     * @param voyageId the unique ID of the voyage
     * @return List of email strings
     */ 
    public List<String> getSubs(String voyageId) {
        List<String> emails = new ArrayList<>();
        List<UserProjection> users = voyageSubDao.findSubs(voyageId);
        
        for (UserProjection user : users) {
            emails.add(user.getemail());
        }
        
        return emails;

    }

}
