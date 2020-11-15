package com.vsta.service;

import java.util.List;

import com.vsta.dao.VesselDAO;
import com.vsta.dto.VesselDTO;
import com.vsta.model.Vessel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Vessel Service tasks that use DAO methods and used for REST APIs for Vessel
 * Object.
 */

@Service
public class VesselService {

    @Autowired
    private VesselDAO vesselDao;

    /**
     * Add Vessel to database
     * 
     * @param vessel Vessel object to be saved
     */
    public void saveVessel(Vessel vessel) {
        vesselDao.save(vessel);
    }

    /**
     * Add Vessels in array to database
     * 
     * @param vessels List of vessel objects
     */
    public void saveVessels(List<Vessel> vessels) {
        vesselDao.saveAll(vessels);
    }

    /**
     * Get all Vessels in database
     * 
     * @param startDate user specified Date with format YYYY-MM-DD to retrieve
     *                  vessels from
     * @param endDate   user specified Date with format YYYY-MM-DD to retrieve
     *                  vessels to
     * @return a list of vesselDTO objects with Berth date from indicated startDate
     *         to indicated endDate.
     */
    public List<VesselDTO> getVesselsByDate(String startDate, String endDate) {
        return vesselDao.findByDate(startDate, endDate);
    }
}
