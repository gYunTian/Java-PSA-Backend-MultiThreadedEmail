package com.portnet.controller.voyage;

import com.portnet.entity.voyage.VoyageIn;
import com.portnet.service.voyage.VoyageInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VoyageInController {

    @Autowired
    private VoyageInService service;

    /**
     * Add methods
     */

    @PostMapping("/addVoyageIn")
    public void addVoyageIn(@RequestBody VoyageIn voyageIn) {
        service.saveVoyageIn(voyageIn);
    }

    @PostMapping("/addVoyageIns")
    public void addVoyageIns(@RequestBody List<VoyageIn> voyageIns) {
        service.saveVoyageIns(voyageIns);
    }

    /**
     * Get methods
     */

    @GetMapping("/voyageIns")
    public List<VoyageIn> findAllVoyageIns() {
        return service.getVoyageIns();
    }

    @GetMapping("/voyageInById/{id}")
    public VoyageIn findVoyageInById(@PathVariable int id) {
        return service.getVoyageInById(id);
    }

    /**
     * Update methods
     */

    @PutMapping("/updateVoyageIn")
    public void updateVoyageIn(@RequestBody VoyageIn voyageIn) {
        service.updateVoyageIn(voyageIn);
    }

}
