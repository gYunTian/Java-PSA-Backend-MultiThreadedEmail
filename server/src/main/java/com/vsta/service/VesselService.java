package com.vsta.service;

import com.vsta.dao.VesselDAO;
import com.vsta.dto.VesselDTO;
import com.vsta.model.Vessel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Vessel Service tasks that use DAO methods
 * and used for REST APIs for Vessel Object.
 */

@Service
public class VesselService {

    @Autowired
    private VesselDAO vesselDao;

    /**
     * Add Vessel to database.
     * @param vessel Vessel object to be saved.
     */
    public void saveVessel(Vessel vessel) {
        vesselDao.save(vessel);
    }

    /**
     * Add Vessels in array to database.
     * @param vessels List of vessel objects.
     */
    public void saveVessels(List<Vessel> vessels) {
        vesselDao.saveAll(vessels);
    }

    /**
     * Get all Vessels in database.
     * @param startDate Date with format YYYY-MM-DD to start retrieving vessels from.
     * @param endDate   Date with format YYYY-MM-DD that retrieval of vessels is to
     *                  be done until.
     * @return List of vesselDTO objects with Berth date from indicated startDate
     *         to indicated endDate.
     */
    public List<VesselDTO> getVesselsByDate(String startDate, String endDate) {
        return vesselDao.findByDate(startDate, endDate);
    }

    /**
     * Get all Vessels of specified uniqueId in database.
     * @param uniqueId ID to uniquely identify a Voyage.
     * @return Vessel object of indicated uniqueId.
     */
    public Vessel getVesselByUniqueId(String uniqueId){
        return vesselDao.findByUniqueId(uniqueId);
    }
}
