package com.vsta.service.voyage;

import com.vsta.dao.voyage.VoyageOutDao;
import com.vsta.entity.voyage.VoyageOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageOutService {

    @Autowired
    private VoyageOutDao voyageOutDao;

    /**
     * Add VoyageOut to database
     * @param voyageOut object
     */
    public void saveVoyageOut(VoyageOut voyageOut) {
        voyageOutDao.save(voyageOut);
    }

    /**
     * Add VoyageOuts in array to database
     * @param voyageOuts array
     */
    public void saveVoyageOuts(List<VoyageOut> voyageOuts) {
        voyageOutDao.saveAll(voyageOuts);
    }
    

    /**
     * Get all VoyageOuts in database
     * @return voyageOuts array
     */
    public List<VoyageOut> getVoyageOuts() {
        return voyageOutDao.findAll();
    }

    /**
     * Get VoyageOut with specified id in database
     * @param id the auto-generated ID of the voyage, identified by vesselName and voyageNumOut
     * @return voyageOut object
     */
    public VoyageOut getVoyageOutById(int id){
        return voyageOutDao.findById(id).orElse(null);
    }

}
