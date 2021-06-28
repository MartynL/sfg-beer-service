package com.mlatta.beer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EurekaClientAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EurekaDiscoveryClientConfiguration;

@EnableCaching
@SpringBootApplication(exclude = {EurekaClientAutoConfiguration.class, EurekaDiscoveryClientConfiguration.class})
public class SfgBeerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SfgBeerServiceApplication.class, args);
	}

}
