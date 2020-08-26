package com.mlatta.beer.service.inventory.impl;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.mlatta.beer.service.inventory.BeerInventoryService;
import com.mlatta.beer.service.inventory.model.BeerInventoryDto;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConfigurationProperties(prefix = "sfg.brewery", ignoreUnknownFields = false)
public class BeerInventoryServiceRestTemplate implements BeerInventoryService {
	
	private static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
	private final RestTemplate restTemplate;
	
	@Setter
	private String beerInventoryServiceHost;
	
	public BeerInventoryServiceRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	@Override
	public Integer getOnHandInventory(UUID beerId) {
		
		log.debug("Calling Inventory Service.");
		
		ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate.exchange(
				beerInventoryServiceHost + INVENTORY_PATH,
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<BeerInventoryDto>>() {},
				(Object) beerId);
		
		return Objects.requireNonNull(responseEntity.getBody())
					  .stream()
					  .mapToInt(BeerInventoryDto::getQuantityOnHand)
					  .sum();
	}

}
