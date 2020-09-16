package com.portnet.service.storage;

import com.portnet.dao.storage.VesselDao;
import com.portnet.entity.storage.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service tasks that use DAO methods
 * - retrieve and modify database
 * - useful for REST APIs for Vessel names
 */

@Service
public class VesselService {

    @Autowired
    private VesselDao vesselDao;

    /**
     * Add Vessel to database
     * @param vessel object
     */
    public void saveVessel(Vessel vessel) {
        vesselDao.save(vessel);
    }

    /**
     * Add Vessels in array to database
     * @param vessels array
     */
    public void saveVessels(List<Vessel> vessels) {
        vesselDao.saveAll(vessels);
    }

    /**
     * Get all Vessels in database
     * @return vessels array
     */
    public List<Vessel> getVessels() {
        return vesselDao.findAll();
    }

    /**
     * Remove Vessel with specified name from database
     * @param name the accepted vessel name
     */
    public void deleteVessel(String name) {
        vesselDao.deleteByName(name);
    }

}
