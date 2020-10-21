package com.portnet.service.storage;

import java.util.List;

import com.portnet.dao.storage.VesselDao;
import com.portnet.entity.storage.Vessel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service tasks that use DAO methods - retrieve and modify database - useful
 * for REST APIs for Vessel
 */

@Service
public class VesselService {

    @Autowired
    private VesselDao vesselDao;

    /**
     * Add Vessel to database
     * 
     * @param vessel object
     */
    public void saveVessel(Vessel vessel) {
        vesselDao.save(vessel);
    }

    /**
     * Add Vessels in array to database
     * 
     * @param vessels array
     */
    public void saveVessels(List<Vessel> vessels) {
        vesselDao.saveAll(vessels);
    }

    /**
     * Get all Vessels in database
     * 
     * @return vessels array
     */

    public List<Vessel> getVesselsByDate(String startDate, String endDate) {
        List<Vessel> vesselList = vesselDao.findByDate(startDate, endDate);

        System.out.println(vesselList.size());
        System.out.println(vesselList.get(0));
        
        return vesselList;
    }
}
