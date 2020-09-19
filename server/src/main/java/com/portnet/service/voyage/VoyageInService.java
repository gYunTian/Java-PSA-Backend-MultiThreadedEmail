package com.portnet.service.voyage;

import com.portnet.dao.voyage.VoyageInDao;
import com.portnet.entity.voyage.Voyage;
import com.portnet.entity.voyage.VoyageIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageInService {

    @Autowired
    private VoyageInDao voyageInDao;

    /**
     * Add VoyageIn to database
     * @param voyageIn object
     */
    public void saveVoyageIn(VoyageIn voyageIn) {
        voyageInDao.save(voyageIn);
    }

    /**
     * Add VoyageIns in array to database
     * @param voyageIns array
     */
    public void saveVoyageIns(List<VoyageIn> voyageIns) {
        voyageInDao.saveAll(voyageIns);
    }


    /**
     * Get all VoyageIns in database
     * @return voyageIns array
     */
    public List<VoyageIn> getVoyageIns() {
        return voyageInDao.findAll();
    }

    /**
     * Get VoyageIn with specified id in database
     * @param id the auto-generated ID of the voyage, identified by vesselName and voyageNumOut
     * @return voyageIn object
     */
    public VoyageIn getVoyageInById(int id) {
        return voyageInDao.findById(id).orElse(null);
    }

    
    /**
     * Update VoyageIn berthDt based on voyageIn from database
     * @param voyageIn object
     */
    public void updateVoyageIn(VoyageIn voyageIn) {
        VoyageIn existingVoyageIn = getVoyageInById(voyageIn.getId());

        existingVoyageIn.setBerthDt(voyageIn.getBerthDt());

        voyageInDao.save(existingVoyageIn);
    }

}
