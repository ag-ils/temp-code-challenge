package com.insidelinestudios.athena;

import com.insidelinestudios.athena.configuration.SecurityConfig;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AthenaApplication.class, SecurityConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AthenaApplicationTests {
    @Autowired
    public TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    public String getRootUrl(){
        return "http://localhost:" + port + "/api/v1";
    }

    @org.junit.Test
    public void contextLoads() {
    }

}
