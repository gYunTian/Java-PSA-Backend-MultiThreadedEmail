package com.portnet.service.storage;

import com.portnet.dao.voyage.VoyageIdDao;
import com.portnet.entity.voyage.VoyageId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoyageIdService {

    @Autowired
    private VoyageIdDao voyageIdDao;

    /**
     * Add VoyageId to database
     * @param voyageId object
     */
    public void saveVoyageId(VoyageId voyageId) {
        voyageIdDao.save(voyageId);
    }

    /**
     * Add VoyageIds in array to database
     * @param voyageIds array
     */
    public void saveVoyageIds(List<VoyageId> voyageIds) {
        voyageIdDao.saveAll(voyageIds);
    }
    

    /**
     * Get all VoyageIds in database
     * @return voyageIds array
     */
    public List<VoyageId> getVoyageIds() {
        return voyageIdDao.findAll();
    }

    /**
     * Get VoyageId with specified id in database
     * @param id the auto-generated ID of the voyage, identified by vesselName and voyageNumId
     * @return voyageId object
     */
    public VoyageId getVoyageIdById(int id) {
        return voyageIdDao.findById(id).orElse(null);
    }

    /**
     * Get VoyageId with specified vesselName in database
     * @param vesselName vessel's short name
     * @return voyageId object
     */
    public List<VoyageId> getVoyageIdByVesselName(String vesselName) {
        return voyageIdDao.findByVesselName(vesselName);
    }

    /**
     * Get VoyageId with specified vesselName and VoyageNumber in database
     * @param vesselName vessel's short name
     * @param voyageNum incoming or outgoing voyage number
     * @return voyageId object
     */
    public VoyageId getVoyageIdByVesselNameAndVoyageNum(String vesselName, String voyageNum) {
        return voyageIdDao.findByVesselNameAndVoyageNum(vesselName, voyageNum);
    }

}
