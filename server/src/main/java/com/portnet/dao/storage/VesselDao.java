package com.portnet.dao.storage;

import com.portnet.entity.storage.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "vessel" table to perform various operations
 */

@Repository
public interface VesselDao extends JpaRepository<Vessel,String> {
    /**
     * Additional custom method to delete Vessel with specified name
     * @param name vessel's short name
     */
    void deleteByName(String name);
}
