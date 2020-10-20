package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.exception.ResourceNotFoundException;
import com.insidelinestudios.athena.model.Trek;
import com.insidelinestudios.athena.repository.TrekRepository;
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
public class TrekController {
    @Autowired
    private TrekRepository trekRepository;

    @GetMapping("/treks")
    public List<Trek> getAllLocations(){
        return trekRepository.findAll();
    }

    @GetMapping("/treks/{id}")
    public ResponseEntity<Trek> getTrekById(@PathVariable(value = "id") Long trekId) throws ResourceNotFoundException {
        Trek trek = trekRepository.findById(trekId)
                .orElseThrow(() -> new ResourceNotFoundException("Trek not found on :: " + trekId));
        return ResponseEntity.ok().body(trek);
    }

    @PostMapping("/treks")
    public ResponseEntity<Trek> createLocation(@Valid @RequestBody Trek trek){
        return ResponseEntity.ok().body(trekRepository.save(trek));
    }

    @PutMapping("/treks/{id}")
    public ResponseEntity<Trek> updateLocation(@PathVariable(value = "id") Long trekId, @Valid @RequestBody Trek trekDetails) throws ResourceNotFoundException {
        Trek trek = trekRepository.findById(trekId)
                .orElseThrow(() -> new ResourceNotFoundException("Trek not found on :: " + trekId));
        trek.setUpdatedBy(trekDetails.getUpdatedBy());
        trek.setUpdatedAt(new Date());


        final Trek updatedTrek = trekRepository.save(trek);
        return ResponseEntity.ok(updatedTrek);
    }

    @DeleteMapping("/treks/{id}")
    public Map<String, Boolean> deleteLocation(@PathVariable(value = "id") Long trekId) throws Exception {
        Trek trek = trekRepository.findById(trekId)
                .orElseThrow(() -> new ResourceNotFoundException("Trek not found on :: " + trekId));

        trekRepository.delete(trek);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
