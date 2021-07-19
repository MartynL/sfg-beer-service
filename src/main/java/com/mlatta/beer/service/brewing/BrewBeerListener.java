package com.mlatta.beer.service.brewing;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mlatta.beer.config.JmsConfig;
import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.events.BrewBeerEvent;
import com.mlatta.beer.events.NewInventoryEvent;
import com.mlatta.beer.model.dto.BeerDto;
import com.mlatta.beer.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewBeerListener {

	private final BeerRepository beerRepository;
	private final JmsTemplate jmsTemplate;
	
	@Transactional
	@JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
	public void listen(BrewBeerEvent event) {
		BeerDto dto = event.getBeerDto();
		
		Beer beer = beerRepository.getOne(dto.getId());
		
		dto.setQuantityOnHand(beer.getQuantityToBrew());
		
		NewInventoryEvent newEvent = new NewInventoryEvent(dto);
		
		log.debug("brewed beer " + beer.getMinOnHand() + " : QOH: " + dto.getQuantityOnHand());
		
		jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newEvent);
	}
}
