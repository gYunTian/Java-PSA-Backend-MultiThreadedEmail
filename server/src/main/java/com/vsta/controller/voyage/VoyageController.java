package com.vsta.controller.voyage;

import com.vsta.entity.voyage.Voyage;
import com.vsta.service.voyage.VoyageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for Voyage
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
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

    /*@PutMapping("/updateVoyage")
    public void updateVoyage(@RequestBody Voyage voyage) {
        service.updateVoyage(voyage);
    }*/

}
