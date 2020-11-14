package com.vsta.controller;

import java.util.List;

import com.vsta.dto.VesselDTO;
import com.vsta.service.VesselService;

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
     * Get method - Get all Vessels in database
     * @param startDate user specified Date with format YYYY-MM-DD to retrieve vessels from
     * @param endDate user specified Date with format YYYY-MM-DD to retrieve vessels to
     * @return ResponseEntity with the given status code and message indicating vessel list is retrieved
     */
    @GetMapping(value = "/vessels", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VesselDTO>> getVesselsByDate(@RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {
        
        List<VesselDTO> vesselList = vesselService.getVesselsByDate(startDate, endDate);
        
        return ResponseEntity.ok(vesselList);  
    }
}
