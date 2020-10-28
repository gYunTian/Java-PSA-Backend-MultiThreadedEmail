package com.portnet.controller.storage;

import java.util.List;

import com.portnet.entity.dto.VesselDTO;
import com.portnet.service.storage.VesselService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST APIs using service methods for Vessel
 */

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
        System.out.println(vesselList.size());
        
        return ResponseEntity.ok(vesselList);  
    }
}
