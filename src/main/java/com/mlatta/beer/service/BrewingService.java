package com.mlatta.beer.service;

import java.util.List;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.mlatta.beer.config.JmsConfig;
import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.events.BrewBeerEvent;
import com.mlatta.beer.model.mappers.BeerMapper;
import com.mlatta.beer.repositories.BeerRepository;
import com.mlatta.beer.service.inventory.BeerInventoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {

	private final BeerRepository beerRepository;
	private final BeerInventoryService beerInventoryService;
	private final JmsTemplate jmsTemplate;
	private final BeerMapper beerMapper;
	
	@Scheduled(fixedRate = 5000)
	public void checkForLowInventory() {
		List<Beer> beers = beerRepository.findAll();
		
		beers.forEach(beer -> {
			Integer invQOH = beerInventoryService.getOnHandInventory(beer.getId());
			
			log.debug("Min on hand is: " + beer.getMinOnHand());
			log.debug("Inventory is: " + invQOH);
			
			if(beer.getMinOnHand() >= invQOH) {
				jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE, new BrewBeerEvent(beerMapper.beerToBeerDto(beer)));
			}
		});
	}
	
}
