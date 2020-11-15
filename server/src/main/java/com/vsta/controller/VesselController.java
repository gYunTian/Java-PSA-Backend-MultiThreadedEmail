package com.vsta.controller;

import java.util.List;

import com.vsta.dto.VesselDTO;
import com.vsta.service.VesselService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST APIs using service methods for Vessel
 */

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class VesselController {

    @Autowired
    private VesselService vesselService;

    /**
     * Get all Vessels in database between a user-specified time period.
     * 
     * @param startDate Date with format YYYY-MM-DD to start retrieving vessels
     *                  from.
     * @param endDate   Date with format YYYY-MM-DD that retrieval of vessels is to
     *                  be done until.
     * @return ResponseEntity with the given status code and message indicating
     *         vessel list is retrieved.
     */
    @GetMapping(value = "/vessels", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VesselDTO>> getVesselsByDate(@RequestParam(value = "startDate") String startDate,
            @RequestParam(value = "endDate") String endDate) {

        List<VesselDTO> vesselList = vesselService.getVesselsByDate(startDate, endDate);
        return ResponseEntity.ok(vesselList);
    }
}
