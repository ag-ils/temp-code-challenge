package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.exception.ResourceNotFoundException;
import com.insidelinestudios.athena.model.Device;
import com.insidelinestudios.athena.repository.DeviceRepository;
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
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @GetMapping("/devices")
    public List<Device> getAllDevices(){
        return deviceRepository.findAll();
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Device> getDeviceById(@PathVariable(value = "id") Long deviceId) throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found on :: " + deviceId));
        return ResponseEntity.ok().body(device);
    }

    @PostMapping("/devices")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device){
        return ResponseEntity.ok().body(deviceRepository.save(device));
    }

    @PutMapping("/devices/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable(value = "id") Long deviceId, @Valid @RequestBody Device deviceDetails) throws ResourceNotFoundException {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found on :: " + deviceId));

        device.setDescription(deviceDetails.getDescription());
        device.setUpdatedAt(new Date());
        device.setUpdatedBy(deviceDetails.getUpdatedBy());

        final Device updatedDevice = deviceRepository.save(device);
        return ResponseEntity.ok(updatedDevice);
    }

    @DeleteMapping("/devices/{id}")
    public Map<String, Boolean> deleteDevice(@PathVariable(value = "id") Long deviceId) throws Exception {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found on :: " + deviceId));

        deviceRepository.delete(device);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
