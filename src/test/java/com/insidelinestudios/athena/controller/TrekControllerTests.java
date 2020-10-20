package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.AthenaApplicationTests;
import com.insidelinestudios.athena.model.Trek;
import com.insidelinestudios.athena.model.User;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class TrekControllerTests extends AthenaApplicationTests {

    private Trek getTrek(String identifier){
        User user = new User();
        user.setEmail(identifier);
        user.setPassword("xyz");
        user.setFirstName("athena");
        user.setLastName("api");
        user.setAddress1("addy1");
        user.setAddress2("addy2");
        user.setCity("city");
        user.setState("state");
        user.setCountry("earth");
        user.setZip("90210-42");

        user.setCreatedBy(identifier);
        user.setUpdatedBy(identifier);
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

        Trek trek = new Trek();
        trek.setUser(userResponseEntity.getBody());
        trek.setCreatedBy(identifier);
        trek.setUpdatedBy(identifier);

        return trek;
    }

    @Test
    public void testCreateTrek(){
        Trek trek = getTrek("testCreateTrek");
        ResponseEntity<Trek> trekResponseEntity = restTemplate.postForEntity(getRootUrl() + "/treks", trek, Trek.class);

        assertNotNull(trekResponseEntity);
        assertEquals(HttpStatus.OK, trekResponseEntity.getStatusCode());
        assertNotNull(trekResponseEntity.getBody());

        Trek readTrekLocation = restTemplate.getForObject(getRootUrl() + "/treks/" + trekResponseEntity.getBody().getId(), Trek.class);

        assertNotNull(readTrekLocation);
    }

    @Test
    public void testGetTrekById(){
        Trek trek = getTrek("testGetTrekById");
        ResponseEntity<Trek> trekResponseEntity = restTemplate.postForEntity(getRootUrl() + "/treks", trek, Trek.class);

        assertNotNull(trekResponseEntity);
        assertEquals(HttpStatus.OK, trekResponseEntity.getStatusCode());
        assertNotNull(trekResponseEntity.getBody());

        Trek readTrek = restTemplate.getForObject(getRootUrl() + "/treks/" + trekResponseEntity.getBody().getId(), Trek.class);
        assertNotNull(readTrek);
        assertEquals(trekResponseEntity.getBody().getId(), readTrek.getId());
    }

    @Test
    public void testGetAllTreks(){
        Trek trek = getTrek("testGetAllTreks");
        ResponseEntity<Trek> trekResponseEntity = restTemplate.postForEntity(getRootUrl() + "/treks", trek, Trek.class);

        assertNotNull(trekResponseEntity);
        assertEquals(HttpStatus.OK, trekResponseEntity.getStatusCode());
        assertNotNull(trekResponseEntity.getBody());

        HttpEntity<Trek[]> entity = new HttpEntity<>(null);

        ResponseEntity<Trek[]> response = restTemplate.exchange(
                getRootUrl() + "/treks",
                HttpMethod.GET,
                entity,
                Trek[].class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateTrek(){
        Trek trek = getTrek("testUpdateTrek");
        ResponseEntity<Trek> trekResponseEntity = restTemplate.postForEntity(getRootUrl() + "/treks", trek, Trek.class);

        assertNotNull(trekResponseEntity);
        assertEquals(HttpStatus.OK, trekResponseEntity.getStatusCode());
        assertNotNull(trekResponseEntity.getBody());

        HttpEntity<Trek> updateTrek = new HttpEntity<>(trekResponseEntity.getBody());

        assertNotNull(updateTrek);
        assertNotNull(updateTrek.getBody());
        updateTrek.getBody().setUpdatedBy("testUpdateTrek-Updated");

        HttpEntity<Trek> response = restTemplate.exchange(
                getRootUrl() + "/treks/" + trekResponseEntity.getBody().getId(),
                HttpMethod.PUT,
                updateTrek,
                Trek.class
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("testUpdateTrek-Updated", response.getBody().getUpdatedBy());
    }

    @Test
    @Ignore
    public void testDeleteTrekLocation(){
        Trek trek = getTrek("testDeleteTrekLocation");
        ResponseEntity<Trek> trekResponseEntity = restTemplate.postForEntity(getRootUrl() + "/treks", trek, Trek.class);

        assertNotNull(trekResponseEntity);
        assertEquals(HttpStatus.OK, trekResponseEntity.getStatusCode());
        assertNotNull(trekResponseEntity.getBody());

        restTemplate.delete(getRootUrl() + "/treks/" + trekResponseEntity.getBody().getId());
        restTemplate.getForObject("/treks/" + trekResponseEntity.getBody().getId(), Trek.class);
    }
}
