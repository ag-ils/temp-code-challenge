package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.exception.ResourceNotFoundException;
import com.insidelinestudios.athena.model.Client;
import com.insidelinestudios.athena.model.Device;
import com.insidelinestudios.athena.repository.ClientRepository;
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
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/clients")
    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Long clientId) throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found on :: " + clientId));
        return ResponseEntity.ok().body(client);
    }

    @PostMapping("/clients")
    public ResponseEntity<Client> createDevice(@Valid @RequestBody Client client){
        return ResponseEntity.ok().body(clientRepository.save(client));
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Long clientId, @Valid @RequestBody Client clientDetails) throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found on :: " + clientId));

        client.setDescription(clientDetails.getDescription());
        client.setUpdatedAt(new Date());

        final Client updatedClient = clientRepository.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/clients/{id}")
    public Map<String, Boolean> deleteClient(@PathVariable(value = "id") Long clientId) throws Exception {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found on :: " + clientId));

        clientRepository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

}
