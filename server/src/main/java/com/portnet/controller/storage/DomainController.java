package com.portnet.controller.storage;

import com.portnet.entity.storage.Domain;
import com.portnet.service.storage.DomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for Domain
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class DomainController {

    @Autowired
    private DomainService service;

    /**
     * Add methods
     */

    @PostMapping("/addDomain")
    public void addDomain(@RequestBody Domain domain) {
        service.saveDomain(domain);
    }

    @PostMapping("/addDomains")
    public void addDomain(@RequestBody List<Domain> domains) {
        service.saveDomains(domains);
    }

    /**
     * Get methods
     */

    @GetMapping("/domains")
    public List<Domain> findAllDomains() {
        return service.getDomains();
    }

    /**
     * Delete methods
     */

    @DeleteMapping("/deleteDomain/{name}")
    public void deleteDomain(@PathVariable String name) {
        service.deleteDomain(name);
    }

}