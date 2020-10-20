package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.exception.ResourceNotFoundException;
import com.insidelinestudios.athena.model.TrekLocation;
import com.insidelinestudios.athena.repository.TrekLocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TrekLocationController {

    @Autowired
    private TrekLocationRepository trekLocationRepository;

    @GetMapping("/trekLocations")
    public List<TrekLocation> getAllTrekLocations(){
        return trekLocationRepository.findAll();
    }

    @GetMapping("/trekLocations/{id}")
    public ResponseEntity<TrekLocation> getTrekLocationById(@PathVariable(value = "id") Long trekLocationId) throws ResourceNotFoundException {
        TrekLocation trekLocation = trekLocationRepository.findById(trekLocationId)
                .orElseThrow(() -> new ResourceNotFoundException("Trek Location not found on :: " + trekLocationId));
        return ResponseEntity.ok().body(trekLocation);
    }

    @PostMapping("/trekLocations")
    public ResponseEntity<TrekLocation> createTrekLocation(@Valid @RequestBody TrekLocation trekLocation){
        return ResponseEntity.ok().body(trekLocationRepository.save(trekLocation));
    }

    @PutMapping("/trekLocations/{id}")
    public ResponseEntity<TrekLocation> updateTrekLocation(@PathVariable(value = "id") Long trekLocationId, @Valid @RequestBody TrekLocation trekLocationDetails) throws ResourceNotFoundException {
        TrekLocation trekLocation = trekLocationRepository.findById(trekLocationId)
                .orElseThrow(() -> new ResourceNotFoundException("Trek Location not found on :: " + trekLocationId));

        trekLocation.setTrek(trekLocationDetails.getTrek());
        trekLocation.setLongitude(trekLocationDetails.getLongitude());
        trekLocation.setLatitude(trekLocationDetails.getLatitude());
        trekLocation.setAltitude(trekLocationDetails.getAltitude());
        trekLocation.setHeading(trekLocationDetails.getHeading());
        trekLocation.setUpdatedAt(new Date());
        trekLocation.setUpdatedBy(trekLocationDetails.getUpdatedBy());

        final TrekLocation updatedTrekLocation = trekLocationRepository.save(trekLocation);
        return ResponseEntity.ok(updatedTrekLocation);
    }

    @DeleteMapping("/trekLocations/{id}")
    public Map<String, Boolean> deleteTrekLocation(@PathVariable(value = "id") Long trekLocationId) throws Exception {
        TrekLocation trekLocation = trekLocationRepository.findById(trekLocationId)
                .orElseThrow(() -> new ResourceNotFoundException("Trek Location not found on :: " + trekLocationId));

        trekLocationRepository.delete(trekLocation);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
