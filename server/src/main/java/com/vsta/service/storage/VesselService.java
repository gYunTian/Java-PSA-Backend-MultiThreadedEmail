package com.vsta.service.storage;

import java.util.ArrayList;
import java.util.List;

import com.vsta.dao.storage.VesselDao;
import com.vsta.dao.storage.VesselDao.VesselProjection;
import com.vsta.entity.dto.VesselDTO;
import com.vsta.entity.storage.Vessel;

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

        List<VesselProjection> vesselRetrieved = vesselDao.findByDate(startDate, endDate);
        List<VesselDTO> vesselList = new ArrayList<>();

        // manual mapping
        for (VesselProjection vesselProjection : vesselRetrieved) {
            vesselList.add(new VesselDTO(vesselProjection.getuniqueId(), vesselProjection.getimoN(),
                    vesselProjection.getfullVslM(), vesselProjection.getabbrVslM(), vesselProjection.getfullInVoyN(),
                    vesselProjection.getinVoyN(), vesselProjection.getoutVoyN(), vesselProjection.getfullInVoyN(),
                    vesselProjection.getshiftSeqN(), vesselProjection.getbthgDt(), vesselProjection.getunbthgDt(),
                    vesselProjection.getberthN(), vesselProjection.getstatus(), vesselProjection.getabbrTerminalM(),
                    vesselProjection.getlast_bthgDt(), vesselProjection.getlast_unbthgDt(),
                    vesselProjection.getbthgDt_change_count(), vesselProjection.getunbthgDt_change_count(),
                    vesselProjection.getfirst_arrival()));
        }

        return vesselList;
    }
}
