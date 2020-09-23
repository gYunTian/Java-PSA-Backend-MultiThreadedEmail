package com.portnet.controller.voyage;

import com.portnet.entity.voyage.VoyageOut;
import com.portnet.service.voyage.VoyageOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VoyageOutController {

    @Autowired
    private VoyageOutService service;

    /**
     * Add methods
     */

    @PostMapping("/addVoyageOut")
    public void addVoyageOut(@RequestBody VoyageOut voyageOut) {
        service.saveVoyageOut(voyageOut);
    }

    @PostMapping("/addVoyageOuts")
    public void addVoyageOuts(@RequestBody List<VoyageOut> voyageOuts) {
        service.saveVoyageOuts(voyageOuts);
    }

    /**
     * Get methods
     */

    @GetMapping("/voyageOuts")
    public List<VoyageOut> findAllVoyageOuts() {
        return service.getVoyageOuts();
    }

    @GetMapping("/voyageOutById/{id}")
    public VoyageOut findVoyageOutById(@PathVariable int id) {
        return service.getVoyageOutById(id);
    }

}
