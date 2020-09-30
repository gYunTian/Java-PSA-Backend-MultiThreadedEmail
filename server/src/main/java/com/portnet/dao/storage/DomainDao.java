package com.portnet.dao.storage;

import com.portnet.entity.storage.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Data Access Objects for "domain" table to perform various operations
 */

@Repository
public interface DomainDao extends JpaRepository<Domain, String> {
    /**
     * Additional custom method to delete Domain with specified name
     * @param name the accepted domain name
     */
    void deleteByName(String name);
}
