package com.vsta.service;

import java.util.List;

import com.vsta.dao.VesselDao;
import com.vsta.dto.VesselDTO;
import com.vsta.entity.Vessel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service tasks that use DAO methods
 * - retrieve and modify database for Vessel Object
 */

@Service
public class VesselService {

    @Autowired
    private VesselDao vesselDao;

    /**
     * Add Vessel to database
     * 
     * @param vessel vessel object
     */
    public void saveVessel(Vessel vessel) {
        vesselDao.save(vessel);
    }

    /**
     * Add Vessels in array to database
     * 
     * @param vessels list of vessel objects
     */
    public void saveVessels(List<Vessel> vessels) {
        vesselDao.saveAll(vessels);
    }
    
    /**
     * Get all Vessels in database
     * @param startDate user specified Date with format YYYY-MM-DD to retrieve vessels from
     * @param endDate user specified Date with format YYYY-MM-DD to retrieve vessels to
     * @return a list of vesselDTO objects
     */

    // use vessel dao for this
    public List<VesselDTO> getVesselsByDate(String startDate, String endDate) {

        List<VesselDTO> vesselRetrieved = vesselDao.findByDate(startDate, endDate);

        return vesselRetrieved;
    }
}
