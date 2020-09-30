package com.portnet.service.storage;

import java.util.List;

import com.portnet.entity.storage.Vessel;
import com.portnet.dao.storage.VesselDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service tasks that use DAO methods
 * - retrieve and modify database
 * - useful for REST APIs for Vessel names
 */

@Service
public class VesselService {

    @Autowired
    private VesselDao vesselRepo;

    /**
     * Add Vessel to database
     * @param vessel object
     */
    public void saveVessel(Vessel vessel) {
        vesselRepo.save(vessel);
    }
    
    /**
     * Add Vessels in array to database
     * @param vessels array
     */
    public void saveVessels(List<Vessel> vessels) {
        vesselRepo.saveAll(vessels);
    }
    
    /**
     * Get all Vessels in database
     * @return vessels array
     */
    public List<Vessel> getVesselsByDate(String startDate, String endDate) {
        return (List<Vessel>) vesselRepo.findByDate(startDate, endDate);
    }
}
