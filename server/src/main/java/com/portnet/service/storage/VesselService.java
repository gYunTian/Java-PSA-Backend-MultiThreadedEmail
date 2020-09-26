package com.portnet.service.storage;

import com.portnet.entity.storage.Vessel;
import com.portnet.repository.VesselRepository;

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
    private VesselRepository vesselRepo;

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
    public List<Vessel> getVessels() {
        return (List<Vessel>) vesselRepo.findAll();
    }
}
