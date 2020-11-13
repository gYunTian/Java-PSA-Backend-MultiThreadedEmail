package com.vsta.controller.storage;

import java.util.List;

import com.vsta.entity.dto.VesselDTO;
import com.vsta.service.storage.VesselService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST APIs using service methods for Vessel
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VesselController {
    
    @Autowired
    private VesselService vesselService;

    /**
     * Get methods
     * @param startDate
     * @param endDate
     */
    @GetMapping(value = "/vessels", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VesselDTO>> getVesselsByDate(@RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {
        
        List<VesselDTO> vesselList = vesselService.getVesselsByDate(startDate, endDate);
        
        return ResponseEntity.ok(vesselList);  
    }
}
