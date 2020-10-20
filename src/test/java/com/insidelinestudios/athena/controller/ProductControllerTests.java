package com.insidelinestudios.athena.controller;

import com.insidelinestudios.athena.AthenaApplicationTests;
import com.insidelinestudios.athena.model.Product;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProductControllerTests extends AthenaApplicationTests {

    private Product getProduct(String description){
        Product product = new Product();
        product.setDescription(description);
        product.setSku("sku");
        product.set
        product.setCreatedBy("test");
        product.setUpdatedBy("test");
        return product;
    }

    @Test
    public void testCreateProduct(){
        Product product = getProduct("testCreateProduct");
        ResponseEntity<Product> productResponseEntity = restTemplate.postForEntity(getRootUrl() + "/products", product, Product.class);

        assertNotNull(productResponseEntity);
        assertEquals(HttpStatus.OK, productResponseEntity.getStatusCode());
        assertNotNull(productResponseEntity.getBody());
    }

    @Test
    public void testgetProductById(){
        Product product = getProduct("testgetProductById");
        ResponseEntity<Product> productResponseEntity = restTemplate.postForEntity(getRootUrl() + "/products", product, Product.class);

        assertNotNull(productResponseEntity);
        assertEquals(HttpStatus.OK, productResponseEntity.getStatusCode());
        assertNotNull(productResponseEntity.getBody());

        Product readClient = restTemplate.getForObject(getRootUrl() + "/products/" + productResponseEntity.getBody().getId(), Product.class);
        assertNotNull(readClient);
        assertEquals(productResponseEntity.getBody().getDescription(), readClient.getDescription());
    }

    @Test
    public void testGetAllProducts(){
        Product product = getProduct("testGetAllProducts");
        ResponseEntity<Product> productResponseEntity = restTemplate.postForEntity(getRootUrl() + "/products", product, Product.class);

        assertNotNull(productResponseEntity);
        assertEquals(HttpStatus.OK, productResponseEntity.getStatusCode());
        assertNotNull(productResponseEntity.getBody());

        HttpEntity<Product[]> entity = new HttpEntity<>(null);

        ResponseEntity<String> response = restTemplate.exchange(
                getRootUrl() + "/products",
                HttpMethod.GET,
                entity,
                String.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testUpdateProduct(){
        Product product = getProduct("testUpdateClient");
        ResponseEntity<Product> productResponseEntity = restTemplate.postForEntity(getRootUrl() + "/products", product, Product.class);

        assertNotNull(productResponseEntity);
        assertEquals(HttpStatus.OK, productResponseEntity.getStatusCode());
        assertNotNull(productResponseEntity.getBody());

        HttpEntity<Product> updateClient = new HttpEntity<>(productResponseEntity.getBody());
        assertNotNull(updateClient);
        assertNotNull(updateClient.getBody());
        updateClient.getBody().setDescription("testUpdateClient-Updated");

        HttpEntity<Product> response = restTemplate.exchange(
                getRootUrl() + "/products/" + productResponseEntity.getBody().getId(),
                HttpMethod.PUT,
                updateClient,
                Product.class
        );

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals("testUpdateClient-Updated", response.getBody().getDescription());
    }

    @Test
    @Ignore
    public void testDeleteProduct(){
        Product product = getProduct("testDeleteProduct");
        ResponseEntity<Product> productResponseEntity = restTemplate.postForEntity(getRootUrl() + "/products", product, Product.class);

        assertNotNull(productResponseEntity);
        assertEquals(HttpStatus.OK, productResponseEntity.getStatusCode());
        assertNotNull(productResponseEntity.getBody());

        restTemplate.delete(getRootUrl() + "/products/" + productResponseEntity.getBody().getId());
        restTemplate.getForObject("/products/" + productResponseEntity.getBody().getId(), Product.class);
    }
}
