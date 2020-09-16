package com.portnet.controller.storage;

import com.portnet.entity.storage.Vessel;
import com.portnet.service.storage.VesselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST APIs using service methods for Vessel
 */

@RestController
public class VesselController {

    @Autowired
    private VesselService service;

    /**
     * Add methods
     */

    @PostMapping("/addVessel")
    public void addVessel(@RequestBody Vessel vessel) {
        service.saveVessel(vessel);
    }

    @PostMapping("/addVessels")
    public void addVessel(@RequestBody List<Vessel> vessels) {
        service.saveVessels(vessels);
    }

    /**
     * Get methods
     */

    @GetMapping("/vessels")
    public List<Vessel> findAllVessels() {
        return service.getVessels();
    }

    /**
     * Delete methods
     */

    @DeleteMapping("/deleteVessel/{name}")
    public void deleteVessel(@PathVariable String name) {
        service.deleteVessel(name);
    }

}