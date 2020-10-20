package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.AthenaApplicationTests;
import com.insidelinestudios.athena.model.Client;
import com.insidelinestudios.athena.model.Device;
import com.insidelinestudios.athena.model.User;
import com.insidelinestudios.athena.model.UserClientDevice;
import com.insidelinestudios.athena.repository.UserClientDeviceRepository;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserClientDeviceControllerTests extends AthenaApplicationTests {

    @Autowired
    private UserClientDeviceRepository userClientDeviceRepository;

    private UserClientDevice getUserClientDevice(String identifier){
        User user = new User();
        user.setEmail(identifier);
        user.setFirstName("UCD");
        user.setLastName("UCD");
        user.setPassword("ucd");
        user.setCreatedBy("test");
        user.setUpdatedBy("test");
        user.setAddress1("addy1");
        user.setAddress2("addy2");
        user.setCity("city");
        user.setState("state");
        user.setCountry("earth");
        user.setZip("90210-42");
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

        Client client = new Client();
        client.setDescription(identifier);
        client.setCreatedBy("test");
        client.setUpdatedBy("test");
        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity(getRootUrl() + "/clients", client, Client.class);

        Device device = new Device();
        device.setDescription(identifier);
        device.setCreatedBy("test");
        device.setUpdatedBy("test");
        ResponseEntity<Device> deviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/devices", device, Device.class);

        UserClientDevice userClientDevice = new UserClientDevice();
        userClientDevice.setUser(userResponseEntity.getBody());
        userClientDevice.setClient(clientResponseEntity.getBody());
        userClientDevice.setDevice(deviceResponseEntity.getBody());
        userClientDevice.setCreatedBy("test");
        userClientDevice.setUpdatedBy("test");

        return userClientDevice;
    }

    @Test
    void testGetAllUserClientDevices() {
        UserClientDevice userClientDevice = getUserClientDevice("testGetAllUserClientDevices");
        ResponseEntity<UserClientDevice> userClientDeviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/userClientDevices", userClientDevice, UserClientDevice.class);

        assertNotNull(userClientDeviceResponseEntity);
        assertEquals(HttpStatus.OK, userClientDeviceResponseEntity.getStatusCode());
        assertNotNull(userClientDeviceResponseEntity.getBody());

        HttpEntity<UserClientDevice[]> entity = new HttpEntity<>(null);

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
    void testGetUserClientDeviceById() {
        UserClientDevice userClientDevice = getUserClientDevice("testGetUserClientDeviceById");
        ResponseEntity<UserClientDevice> userClientDeviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/userClientDevices", userClientDevice, UserClientDevice.class);

        assertNotNull(userClientDeviceResponseEntity);
        assertEquals(HttpStatus.OK, userClientDeviceResponseEntity.getStatusCode());
        assertNotNull(userClientDeviceResponseEntity.getBody());

        UserClientDevice readUserClientDevice = restTemplate.getForObject(getRootUrl() + "/userClientDevices/" + userClientDeviceResponseEntity.getBody().getId(), UserClientDevice.class);
        assertNotNull(readUserClientDevice);
        assertEquals(userClientDeviceResponseEntity.getBody().getId(), readUserClientDevice.getId());
    }

    @Test
    void testGetUserClientDevicesByUserId() {
        UserClientDevice userClientDevice = getUserClientDevice("testGetUserClientDevicesByUserId");
        ResponseEntity<UserClientDevice> userClientDeviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/userClientDevices", userClientDevice, UserClientDevice.class);

        assertNotNull(userClientDeviceResponseEntity);
        assertEquals(HttpStatus.OK, userClientDeviceResponseEntity.getStatusCode());
        assertNotNull(userClientDeviceResponseEntity.getBody());

        HttpEntity<UserClientDevice[]> entity = new HttpEntity<>(null);
        ResponseEntity<UserClientDevice[]> response = restTemplate.exchange(
                getRootUrl() + "/userClientDevices/user/" + userClientDeviceResponseEntity.getBody().getUser().getId(),
                HttpMethod.GET,
                entity,
                UserClientDevice[].class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetUserClientDevicesByClientId() {
        UserClientDevice userClientDevice = getUserClientDevice("testGetUserClientDevicesByClientId");
        ResponseEntity<UserClientDevice> userClientDeviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/userClientDevices", userClientDevice, UserClientDevice.class);

        assertNotNull(userClientDeviceResponseEntity);
        assertEquals(HttpStatus.OK, userClientDeviceResponseEntity.getStatusCode());
        assertNotNull(userClientDeviceResponseEntity.getBody());

        HttpEntity<UserClientDevice[]> entity = new HttpEntity<>(null);
        ResponseEntity<UserClientDevice[]> response = restTemplate.exchange(
                getRootUrl() + "/userClientDevices/client/" + userClientDeviceResponseEntity.getBody().getUser().getId(),
                HttpMethod.GET,
                entity,
                UserClientDevice[].class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testGetUserClientDevicesByDeviceId() {
        UserClientDevice userClientDevice = getUserClientDevice("testGetUserClientDevicesByDeviceId");
        ResponseEntity<UserClientDevice> userClientDeviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/userClientDevices", userClientDevice, UserClientDevice.class);

        assertNotNull(userClientDeviceResponseEntity);
        assertEquals(HttpStatus.OK, userClientDeviceResponseEntity.getStatusCode());
        assertNotNull(userClientDeviceResponseEntity.getBody());

        HttpEntity<UserClientDevice[]> entity = new HttpEntity<>(null);
        ResponseEntity<UserClientDevice[]> response = restTemplate.exchange(
                getRootUrl() + "/userClientDevices/device/" + userClientDeviceResponseEntity.getBody().getUser().getId(),
                HttpMethod.GET,
                entity,
                UserClientDevice[].class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void testCreateUserClientDevice() {
        UserClientDevice userClientDevice = getUserClientDevice("testCreateUserClientDevice");
        ResponseEntity<UserClientDevice> userClientDeviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/userClientDevices", userClientDevice, UserClientDevice.class);

        assertNotNull(userClientDeviceResponseEntity);
        assertEquals(HttpStatus.OK, userClientDeviceResponseEntity.getStatusCode());
        assertNotNull(userClientDeviceResponseEntity.getBody());
    }

    @Test
    void testUpdateUserClientDevice() {
        UserClientDevice userClientDevice = getUserClientDevice("testUpdateUserClientDevice");
        ResponseEntity<UserClientDevice> userClientDeviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/userClientDevices", userClientDevice, UserClientDevice.class);

        assertNotNull(userClientDeviceResponseEntity);
        assertEquals(HttpStatus.OK, userClientDeviceResponseEntity.getStatusCode());
        assertNotNull(userClientDeviceResponseEntity.getBody());

        HttpEntity<UserClientDevice> updateUserClientDeviceEntity = new HttpEntity<>(userClientDeviceResponseEntity.getBody());
        assertNotNull(updateUserClientDeviceEntity);
        assertNotNull(updateUserClientDeviceEntity.getBody());
        updateUserClientDeviceEntity.getBody().setUpdatedBy("testUpdateUserClientDevice-Updated");

        HttpEntity<UserClientDevice> response = restTemplate.exchange(
                getRootUrl() + "/userClientDevices/" + userClientDeviceResponseEntity.getBody().getId(),
                HttpMethod.PUT,
                updateUserClientDeviceEntity,
                UserClientDevice.class
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("testUpdateUserClientDevice-Updated", response.getBody().getUpdatedBy());
    }

    @Test
    @Ignore
    void testDeleteUserClientDevice() {
        UserClientDevice userClientDevice = getUserClientDevice("testDeleteUserClientDevice");
        ResponseEntity<UserClientDevice> userClientDeviceResponseEntity = restTemplate.postForEntity(getRootUrl() + "/userClientDevices", userClientDevice, UserClientDevice.class);

        assertNotNull(userClientDeviceResponseEntity);
        assertEquals(HttpStatus.OK, userClientDeviceResponseEntity.getStatusCode());
        assertNotNull(userClientDeviceResponseEntity.getBody());

        restTemplate.delete(getRootUrl() + "/userClientDevices/" + userClientDeviceResponseEntity.getBody().getId());
        restTemplate.getForObject("/userClientDevices/" + userClientDeviceResponseEntity.getBody().getId(), Device.class);
    }
}