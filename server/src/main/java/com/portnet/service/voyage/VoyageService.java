package com.portnet.service.voyage;

import com.portnet.dao.voyage.VoyageDao;
import com.portnet.entity.voyage.Voyage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageService {

    @Autowired
    private VoyageDao voyageDao;

    /**
     * Add Voyage to database
     * @param voyage object
     */
    public void saveVoyage(Voyage voyage) {
        voyageDao.save(voyage);
    }

    /**
     * Add Voyages in array to database
     * @param voyages array
     */
    public void saveVoyages(List<Voyage> voyages) {
        voyageDao.saveAll(voyages);
    }
    

    /**
     * Get all Voyages in database
     * @return voyage object
     */
    public List<Voyage> getVoyage() {
        return voyageDao.findAll();
    }

    /**
     * Get Voyage with specified id in database
     * @param voyageId the auto-generated ID of the voyage, identified by vesselName and voyageNum
     * @return voyage object
     */
    public Voyage getVoyageById(int voyageId) {
        return voyageDao.findById(voyageId).orElse(null);
    }


    /**
     * Update Voyage changeCount based on voyage from database
     * @param voyage object
     */
    /*public void updateVoyage(Voyage voyage) {
        Voyage existingVoyage = getVoyageById(voyage.getVoyageId());
        existingVoyage.setChangeCount(voyage.getChangeCount());
        voyageDao.save(existingVoyage);
    }*/

}
