package com.insidelinestudios.athena;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class AthenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AthenaApplication.class, args);
	}

}
