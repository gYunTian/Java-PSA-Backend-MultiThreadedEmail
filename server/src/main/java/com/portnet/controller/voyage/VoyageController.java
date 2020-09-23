package com.portnet.controller.voyage;

import com.portnet.entity.voyage.Voyage;
import com.portnet.service.voyage.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for Voyage
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Service
public class VoyageController {

    @Autowired
    private VoyageService service;

    /**
     * Add methods
     */

    @PostMapping("/addVoyage")
    public void addVoyage(@RequestBody Voyage voyage) {
        service.saveVoyage(voyage);
    }

    @PostMapping("/addVoyages")
    public void addVoyages(@RequestBody List<Voyage> voyages) {
        service.saveVoyages(voyages);
    }

    /**
     * Get methods
     */

    @GetMapping("/voyages")
    public List<Voyage> findAllVoyages() {
        return service.getVoyage();
    }

    @GetMapping("/voyageById/{id}")
    public Voyage findVoyageById(@PathVariable int id) {
        return service.getVoyageById(id);
    }

    /**
     * Update methods
     */

    @PutMapping("/updateVoyage")
    public void updateVoyage(@RequestBody Voyage voyage) {
        service.updateVoyage(voyage);
    }

}
