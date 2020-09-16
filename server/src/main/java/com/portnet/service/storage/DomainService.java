package com.portnet.service.storage;

import com.portnet.dao.storage.DomainDao;
import com.portnet.entity.storage.Domain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service tasks that use DAO methods
 * - retrieve and modify database
 * - useful for REST APIs for Domain names
 */

@Service
public class DomainService {

    @Autowired
    private DomainDao domainDao;

    /**
     * Add Domain to database
     * @param domain object
     */
    public void saveDomain(Domain domain) {
        domainDao.save(domain);
    }

    /**
     * Add Domains in array to database
     * @param domains array
     */
    public void saveDomains(List<Domain> domains) {
        domainDao.saveAll(domains);
    }


    /**
     * Get all Domains in database
     * @return domains array
     */
    public List<Domain> getDomains() {
        return domainDao.findAll();
    }

    /**
     * Remove Domain with specified name from database
     * @param name the accepted domain name
     */
    public void deleteDomain(String name) {
        domainDao.deleteByName(name);
    }

}
