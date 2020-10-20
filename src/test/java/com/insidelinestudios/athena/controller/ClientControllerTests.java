package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.AthenaApplicationTests;
import com.insidelinestudios.athena.model.Client;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClientControllerTests extends AthenaApplicationTests {

    private Client getClient(String description){
        Client client = new Client();
        client.setDescription(description);
        client.setCreatedBy("test");
        client.setUpdatedBy("test");
        return client;
    }

    @Test
    public void testCreateDevice(){
        Client client = getClient("testCreateClient");
        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity(getRootUrl() + "/clients", client, Client.class);

        assertNotNull(clientResponseEntity);
        assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode());
        assertNotNull(clientResponseEntity.getBody());
    }

    @Test
    public void testGetClientById(){
        Client client = getClient("testGetClientById");
        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity(getRootUrl() + "/clients", client, Client.class);

        assertNotNull(clientResponseEntity);
        assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode());
        assertNotNull(clientResponseEntity.getBody());

        Client readClient = restTemplate.getForObject(getRootUrl() + "/clients/" + clientResponseEntity.getBody().getId(), Client.class);
        assertNotNull(readClient);
        assertEquals(clientResponseEntity.getBody().getDescription(), readClient.getDescription());
    }

    @Test
    public void testGetAllClients(){
        Client client = getClient("testGetAllClients");
        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity(getRootUrl() + "/clients", client, Client.class);

        assertNotNull(clientResponseEntity);
        assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode());
        assertNotNull(clientResponseEntity.getBody());

        HttpEntity<Client[]> entity = new HttpEntity<>(null);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/clients",
                HttpMethod.GET,
                entity,
                String.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateClient(){
        Client client = getClient("testUpdateClient");
        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity(getRootUrl() + "/clients", client, Client.class);

        assertNotNull(clientResponseEntity);
        assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode());
        assertNotNull(clientResponseEntity.getBody());

        HttpEntity<Client> updateClient = new HttpEntity<>(clientResponseEntity.getBody());
        assertNotNull(updateClient);
        assertNotNull(updateClient.getBody());
        updateClient.getBody().setDescription("testUpdateClient-Updated");

        HttpEntity<Client> response = restTemplate.exchange(
                getRootUrl() + "/clients/" + clientResponseEntity.getBody().getId(),
                HttpMethod.PUT,
                updateClient,
                Client.class
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("testUpdateClient-Updated", response.getBody().getDescription());
    }

    @Test
    @Ignore
    public void testDeleteClient(){
        Client client = getClient("testDeleteClient");
        ResponseEntity<Client> clientResponseEntity = restTemplate.postForEntity(getRootUrl() + "/clients", client, Client.class);

        assertNotNull(clientResponseEntity);
        assertEquals(HttpStatus.OK, clientResponseEntity.getStatusCode());
        assertNotNull(clientResponseEntity.getBody());

        restTemplate.delete(getRootUrl() + "/clients/" + clientResponseEntity.getBody().getId());
        restTemplate.getForObject("/clients/" + clientResponseEntity.getBody().getId(), Client.class);
    }
}
