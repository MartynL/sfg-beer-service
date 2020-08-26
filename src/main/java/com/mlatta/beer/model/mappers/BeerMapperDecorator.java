package com.mlatta.beer.model.mappers;

import org.springframework.beans.factory.annotation.Autowired;

import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.model.dto.BeerDto;
import com.mlatta.beer.service.inventory.BeerInventoryService;

public abstract class BeerMapperDecorator implements BeerMapper {
	
	private BeerInventoryService beerInventoryService;
	private BeerMapper mapper;
	
	@Autowired
	public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
		this.beerInventoryService = beerInventoryService;
	}

	@Autowired
	public void setMapper(BeerMapper mapper) {
		this.mapper = mapper;
	}

	@Override
	public BeerDto beerToBeerDto(Beer beer, boolean showInventoryOnHand) {
		BeerDto dto = mapper.beerToBeerDto(beer);
		
		if(showInventoryOnHand) {
			dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
		} 
		
		return dto;
	}

	@Override
	public Beer beerDtoToBeer(BeerDto beerDto) {
		return mapper.beerDtoToBeer(beerDto);
	}

}
