package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.exception.ResourceNotFoundException;
import com.insidelinestudios.athena.model.UserClientDevice;
import com.insidelinestudios.athena.repository.UserClientDeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class UserClientDeviceController {

    @Autowired
    private UserClientDeviceRepository userClientDeviceRepository;

    @GetMapping("/userClientDevices")
    public List<UserClientDevice> getAllUserClientDevices(){
        return userClientDeviceRepository.findAll();
    }

    @GetMapping("/userClientDevices/{id}")
    public ResponseEntity<UserClientDevice> getUserClientDeviceById(@PathVariable(value = "id") Long userClientDeviceId) throws ResourceNotFoundException {
        UserClientDevice userClientDevice = userClientDeviceRepository.findById(userClientDeviceId)
                .orElseThrow(() -> new ResourceNotFoundException("UserClientDevice not found on :: " + userClientDeviceId));
        return ResponseEntity.ok().body(userClientDevice);
    }

    @GetMapping("userClientDevices/user/{id}")
    @ResponseBody
    public ResponseEntity<List<UserClientDevice>> getUserClientDevicesByUserId(@PathVariable(value = "id") Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userClientDeviceRepository.getUserClientDevicesByUserId(userId));
    }

    @GetMapping("userClientDevices/client/{id}")
    @ResponseBody
    public ResponseEntity<List<UserClientDevice>> getUserClientDevicesByClientId(@PathVariable(value = "id") Long clientId) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userClientDeviceRepository.getUserClientDevicesByClientId(clientId));
    }

    @GetMapping("userClientDevices/device/{id}")
    @ResponseBody
    public ResponseEntity<List<UserClientDevice>> getUserClientDevicesByDeviceId(@PathVariable(value = "id") Long deviceId) throws ResourceNotFoundException {
        return ResponseEntity.ok().body(userClientDeviceRepository.getUserClientDevicesByDeviceId(deviceId));
    }

    @PostMapping("/userClientDevices")
    public ResponseEntity<UserClientDevice> createUserClientDevice(@Valid @RequestBody UserClientDevice userClientDevice){
        return ResponseEntity.ok().body(userClientDeviceRepository.save(userClientDevice));
    }

    @PutMapping("/userClientDevices/{id}")
    public ResponseEntity<UserClientDevice> updateUserClientDevice(@PathVariable(value = "id") Long locationId, @Valid @RequestBody UserClientDevice userClientDeviceDetails) throws ResourceNotFoundException {
        UserClientDevice userClientDevice = userClientDeviceRepository.findById(locationId)
                .orElseThrow(() -> new ResourceNotFoundException("UserClientDevice not found on :: " + locationId));

        userClientDevice.setClient(userClientDeviceDetails.getClient());
        userClientDevice.setDevice(userClientDeviceDetails.getDevice());
        userClientDevice.setUpdatedAt(new Date());
        userClientDevice.setUpdatedBy(userClientDeviceDetails.getUpdatedBy());

        final UserClientDevice updatedUserClientDevice = userClientDeviceRepository.save(userClientDevice);
        return ResponseEntity.ok(updatedUserClientDevice);
    }

    @DeleteMapping("/userClientDevices/{id}")
    public Map<String, Boolean> deleteUserClientDevice(@PathVariable(value = "id") Long userClientDeviceId) throws Exception {
        UserClientDevice userClientDevice = userClientDeviceRepository.findById(userClientDeviceId)
                .orElseThrow(() -> new ResourceNotFoundException("UserClientDevice not found on :: " + userClientDeviceId));

        userClientDeviceRepository.delete(userClientDevice);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
