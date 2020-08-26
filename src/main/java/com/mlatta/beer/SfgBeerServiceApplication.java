package com.mlatta.beer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SfgBeerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SfgBeerServiceApplication.class, args);
	}

}
