package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.AthenaApplicationTests;
import com.insidelinestudios.athena.model.Trek;
import com.insidelinestudios.athena.model.TrekLocation;
import com.insidelinestudios.athena.model.User;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrekLocationControllerTests extends AthenaApplicationTests {

    public TrekLocation getTrekLocation(String identifier){
        User user = new User();
        user.setEmail(identifier);
        user.setPassword("xyz");
        user.setFirstName("athena");
        user.setLastName("api");
        user.setCreatedBy(identifier);
        user.setUpdatedBy(identifier);
        user.setAddress1("addy1");
        user.setAddress2("addy2");
        user.setCity("city");
        user.setState("state");
        user.setCountry("earth");
        user.setZip("90210-42");
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

        Trek trek = new Trek();
        trek.setUser(userResponseEntity.getBody());
        trek.setCreatedBy(identifier);
        trek.setUpdatedBy(identifier);
        ResponseEntity<Trek> trekResponseEntity = restTemplate.postForEntity(getRootUrl() + "/treks", trek, Trek.class);

        TrekLocation trekLocation = new TrekLocation();
        trekLocation.setTrek(trekResponseEntity.getBody());
        trekLocation.setLongitude("0.0");
        trekLocation.setLatitude("0.0");
        trekLocation.setAltitude("0.0");
        trekLocation.setHeading("0.0");
        trekLocation.setCreatedBy(identifier);
        trekLocation.setUpdatedBy(identifier);
        return trekLocation;
    }

    @Test
    public void testCreateTrekLocation(){
        TrekLocation trekLocation = getTrekLocation("testCreateTrekLocation");
        ResponseEntity<TrekLocation> trekLocationResponseEntity = restTemplate.postForEntity(getRootUrl() + "/trekLocations", trekLocation, TrekLocation.class);

        assertNotNull(trekLocationResponseEntity);
        assertEquals(HttpStatus.OK, trekLocationResponseEntity.getStatusCode());
        assertNotNull(trekLocationResponseEntity.getBody());

        TrekLocation readTrekLocation = restTemplate.getForObject(getRootUrl() + "/trekLocations/" + trekLocationResponseEntity.getBody().getId(), TrekLocation.class);

        assertNotNull(readTrekLocation);
    }

    @Test
    public void testGetTrekLocationById(){
        TrekLocation trekLocation = getTrekLocation("testGetTrekLocationById");
        ResponseEntity<TrekLocation> trekLocationResponseEntity = restTemplate.postForEntity(getRootUrl() + "/trekLocations", trekLocation, TrekLocation.class);

        assertNotNull(trekLocationResponseEntity);
        assertEquals(HttpStatus.OK, trekLocationResponseEntity.getStatusCode());
        assertNotNull(trekLocationResponseEntity.getBody());

        TrekLocation readTrekLocation = restTemplate.getForObject(getRootUrl() + "/trekLocations/" + trekLocationResponseEntity.getBody().getId(), TrekLocation.class);
        assertNotNull(readTrekLocation);
        assertEquals(trekLocationResponseEntity.getBody().getTrek().getId(), readTrekLocation.getTrek().getId());
    }

    @Test
    public void testGetAllTrekLocations(){
        TrekLocation trekLocation = getTrekLocation("testGetAllTrekLocations");
        ResponseEntity<TrekLocation> trekLocationResponseEntity = restTemplate.postForEntity(getRootUrl() + "/trekLocations", trekLocation, TrekLocation.class);

        assertNotNull(trekLocationResponseEntity);
        assertEquals(HttpStatus.OK, trekLocationResponseEntity.getStatusCode());
        assertNotNull(trekLocationResponseEntity.getBody());

        HttpEntity<TrekLocation[]> entity = new HttpEntity<>(null);

        ResponseEntity<TrekLocation[]> response = restTemplate.exchange(
                getRootUrl() + "/trekLocations",
                HttpMethod.GET,
                entity,
                TrekLocation[].class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateTrekLocation(){
        TrekLocation trekLocation = getTrekLocation("testUpdateTrekLocation");
        ResponseEntity<TrekLocation> trekLocationResponseEntity = restTemplate.postForEntity(getRootUrl() + "/trekLocations", trekLocation, TrekLocation.class);

        assertNotNull(trekLocationResponseEntity);
        assertEquals(HttpStatus.OK, trekLocationResponseEntity.getStatusCode());
        assertNotNull(trekLocationResponseEntity.getBody());

        HttpEntity<TrekLocation> updateTrekLocation = new HttpEntity<>(trekLocationResponseEntity.getBody());

        assertNotNull(updateTrekLocation);
        assertNotNull(updateTrekLocation.getBody());
        updateTrekLocation.getBody().setLatitude("1.1");
        updateTrekLocation.getBody().setLongitude("1.1");
        updateTrekLocation.getBody().setAltitude("1.1");
        updateTrekLocation.getBody().setHeading("1.1");

        HttpEntity<TrekLocation> response = restTemplate.exchange(
                getRootUrl() + "/trekLocations/" + trekLocationResponseEntity.getBody().getId(),
                HttpMethod.PUT,
                updateTrekLocation,
                TrekLocation.class
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("1.1", response.getBody().getLongitude());
        assertEquals("1.1", response.getBody().getLatitude());
        assertEquals("1.1", response.getBody().getAltitude());
        assertEquals("1.1", response.getBody().getHeading());
    }

    @Test
    @Ignore
    public void testDeleteTrekLocation(){
        TrekLocation trekLocation = getTrekLocation("testDeleteTrekLocation(");
        ResponseEntity<TrekLocation> trekLocationResponseEntity = restTemplate.postForEntity(getRootUrl() + "/trekLocations", trekLocation, TrekLocation.class);

        assertNotNull(trekLocationResponseEntity);
        assertEquals(HttpStatus.OK, trekLocationResponseEntity.getStatusCode());
        assertNotNull(trekLocationResponseEntity.getBody());

        restTemplate.delete(getRootUrl() + "/trekLocations/" + trekLocationResponseEntity.getBody().getId());
        restTemplate.getForObject("/trekLocations/" + trekLocationResponseEntity.getBody().getId(), TrekLocation.class);
    }
}
