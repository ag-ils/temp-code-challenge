package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.AthenaApplicationTests;
import com.insidelinestudios.athena.model.Device;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DeviceControllerTests extends AthenaApplicationTests {

    private Device getDevice(String description){
        Device device = new Device();
        device.setDescription(description);
        device.setCreatedBy("test");
        device.setUpdatedBy("test");
        return device;
    }

    @Test
    public void testCreateDevice(){
        Device device = getDevice("testCreateDevice");
        ResponseEntity<Device> deviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/devices", device, Device.class);

        assertNotNull(deviceResponseEntity);
        assertEquals(HttpStatus.OK, deviceResponseEntity.getStatusCode());
        assertNotNull(deviceResponseEntity.getBody());
    }

    @Test
    public void testGetDeviceById(){
        Device device = getDevice("testGetDeviceById");
        ResponseEntity<Device> deviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/devices", device, Device.class);

        assertNotNull(deviceResponseEntity);
        assertEquals(HttpStatus.OK, deviceResponseEntity.getStatusCode());
        assertNotNull(deviceResponseEntity.getBody());

        Device readDevice = restTemplate.getForObject(getRootUrl() + "/devices/" + deviceResponseEntity.getBody().getId(), Device.class);
        assertNotNull(readDevice);
        assertEquals(deviceResponseEntity.getBody().getDescription(), readDevice.getDescription());
    }

    @Test
    public void testGetAllDevices(){
        Device device = getDevice("testGetAllDevices");
        ResponseEntity<Device> deviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/devices", device, Device.class);

        assertNotNull(deviceResponseEntity);
        assertEquals(HttpStatus.OK, deviceResponseEntity.getStatusCode());
        assertNotNull(deviceResponseEntity.getBody());

        HttpEntity<Device[]> entity = new HttpEntity<>(null);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/devices",
                HttpMethod.GET,
                entity,
                String.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateDevice(){
        Device device = getDevice("testUpdateDevice");
        ResponseEntity<Device> deviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/devices", device, Device.class);

        assertNotNull(deviceResponseEntity);
        assertEquals(HttpStatus.OK, deviceResponseEntity.getStatusCode());
        assertNotNull(deviceResponseEntity.getBody());

        HttpEntity<Device> updateDevice = new HttpEntity<>(deviceResponseEntity.getBody());
        assertNotNull(updateDevice);
        assertNotNull(updateDevice.getBody());
        updateDevice.getBody().setDescription("testUpdateDevice-Updated");

        HttpEntity<Device> response = restTemplate.exchange(
                getRootUrl() + "/devices/" + deviceResponseEntity.getBody().getId(),
                HttpMethod.PUT,
                updateDevice,
                Device.class
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("testUpdateDevice-Updated", response.getBody().getDescription());
    }

    @Test
    @Ignore
    public void testDeleteDevice(){
        Device device = getDevice("testDeleteDevice");
        ResponseEntity<Device> deviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/devices", device, Device.class);

        assertNotNull(deviceResponseEntity);
        assertEquals(HttpStatus.OK, deviceResponseEntity.getStatusCode());
        assertNotNull(deviceResponseEntity.getBody());

        restTemplate.delete(getRootUrl() + "/devices/" + deviceResponseEntity.getBody().getId());
        restTemplate.getForObject("/devices/" + deviceResponseEntity.getBody().getId(), Device.class);
    }
}
