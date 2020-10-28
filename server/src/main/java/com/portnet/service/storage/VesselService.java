package com.portnet.service.storage;

import java.util.ArrayList;
import java.util.List;

import com.portnet.dao.storage.VesselDao;
import com.portnet.dao.storage.VesselDao.VesselAll;
import com.portnet.entity.dto.VesselDTO;
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

    // use vessel dao for this
    public List<VesselDTO> getVesselsByDate(String startDate, String endDate) {

        List<VesselAll> vesselRetrieved = vesselDao.findByDate(startDate, endDate);
        List<VesselDTO> vesselList = new ArrayList<>();
        
        // manual mapping
        for (VesselAll vesselAll : vesselRetrieved) {
            vesselList.add(
                new VesselDTO(vesselAll.getuniqueId(), vesselAll.getimoN(), vesselAll.getfullVslM(), vesselAll.getabbrVslM(), vesselAll.getfullInVoyN(),
                vesselAll.getinVoyN(), vesselAll.getoutVoyN(), vesselAll.getfullInVoyN(), 
                vesselAll.getshiftSeqN(), vesselAll.getbthgDt(), vesselAll.getunbthgDt(),
                vesselAll.getberthN(), vesselAll.getstatus(), vesselAll.getabbrTerminalM(), vesselAll.getlast_bthgDt(), 
                vesselAll.getlast_unbthgDt(), vesselAll.getbthgDt_change_count(), 
                vesselAll.getunbthgDt_change_count(), vesselAll.getfirst_arrival())
            );
        }

        return vesselList;
    }
}
