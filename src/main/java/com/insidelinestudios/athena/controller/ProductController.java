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
public class ProductController {

    @Autowired
    private ProductRepository productController;

    @GetMapping("/products")
    public List<Client> getAllProducts(){
        return productController.findAll();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Client> getProductById(@PathVariable(value = "id") Long clientId) throws ResourceNotFoundException {
        Client client = productController.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found on :: " + clientId));
        return ResponseEntity.ok().body(client);
    }

    @PostMapping("/products")
    public ResponseEntity<Client> createProduct(@Valid @RequestBody Client client){
        return ResponseEntity.ok().body(productController.save(client));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<Client> updateProduct(@PathVariable(value = "id") Long clientId, @Valid @RequestBody Client clientDetails) throws ResourceNotFoundException {
        Client client = productController.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found on :: " + clientId));

        client.setDescription(clientDetails.getDescription());
        client.setUpdatedAt(new Date());

        final Client updatedClient = productController.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/products/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long clientId) throws Exception {
        Client client = productController.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found on :: " + clientId));

        productController.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

}
