package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.AthenaApplicationTests;
import com.insidelinestudios.athena.model.User;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.junit.Assert.*;

public class UserControllerTests extends AthenaApplicationTests {

    private User getUser(String email){
        User user = new User();
        user.setEmail(email);
        user.setPassword("xyz");
        user.setFirstName("athena");
        user.setLastName("api");
        user.setCreatedBy("test");
        user.setUpdatedBy("test");
        user.setAddress1("addy1");
        user.setAddress2("addy2");
        user.setCity("city");
        user.setState("state");
        user.setCountry("earth");
        user.setZip("90210-42");
        return user;
    }

    @Test
    public void testCreateUser(){
        User user = getUser("email@testcreateuser.com");
        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());
    }

    @Test
    public void testValidUserCredentials(){
        User user = getUser("email@testValidUserCredentials.com");
        ResponseEntity<User> userResponseEntity = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

        assertNotNull(userResponseEntity);
        assertEquals(HttpStatus.OK, userResponseEntity.getStatusCode());
        assertNotNull(userResponseEntity.getBody());

        HttpEntity<Boolean> entity = new HttpEntity<>(null);

        ResponseEntity<Boolean> response = restTemplate.exchange(
                getRootUrl() + "/users/validate/"+userResponseEntity.getBody().getEmail()+"/" + userResponseEntity.getBody().getPassword(),
                HttpMethod.GET,
                entity,
                Boolean.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Boolean.TRUE, response.getBody());
    }

    @Test
    public void testInvalidUserCredentials(){
        HttpEntity<Boolean> entity = new HttpEntity<>(null);

        ResponseEntity<Boolean> response = restTemplate.exchange(
                getRootUrl() + "/users/validate/4200000/someReallyBadPassword",
                HttpMethod.GET,
                entity,
                Boolean.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(Boolean.FALSE, response.getBody());
    }

    @Test
    public void testGetAllUsers(){
        User user = getUser("email@testgetAllUsers.com");
        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());

        HttpEntity<User[]> entity = new HttpEntity<>(null);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/users",
                HttpMethod.GET,
                entity,
                String.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetUserById(){
        User user = getUser("email@testgetusersbyid.com");
        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());

        User readUser = restTemplate.getForObject(getRootUrl() + "/users/" + postResponse.getBody().getId(), User.class);

        assertNotNull(readUser);
        assertEquals(readUser.getEmail(), user.getEmail());
    }

    @Test
    public void testUpdateUser(){
        User user = getUser("email@testupdateuser.com");
        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());

        HttpEntity<User> updateUser = new HttpEntity<>(postResponse.getBody());
        assertNotNull(updateUser);
        assertNotNull(updateUser.getBody());
        updateUser.getBody().setFirstName("admin1");
        updateUser.getBody().setLastName("admin2");

        HttpEntity<User> response = restTemplate.exchange(
                getRootUrl() + "/users/" + postResponse.getBody().getId(),
                HttpMethod.PUT,
                updateUser,
                User.class
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("admin1", response.getBody().getFirstName());
        assertEquals("admin2", response.getBody().getLastName());
    }

    @Ignore // IGNORING THIS TEST UNLESS WE WOULD LIKE THE API TO DELETE FROM THE DB, TEST FAILS DUE TO RESTRICTIONS FROM TestRestTemplate
    @Test
    public void testDeleteUser() {
        User user = getUser("email@testdeleteuser.com");
        ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);

        assertNotNull(postResponse);
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
        assertNotNull(postResponse.getBody());

        restTemplate.delete(getRootUrl() + "/users/" + postResponse.getBody().getId());
        restTemplate.getForObject("/users/" + postResponse.getBody().getId(), User.class);
   }
}
