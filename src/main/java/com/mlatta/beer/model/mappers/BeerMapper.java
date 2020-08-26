package com.mlatta.beer.model.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.model.dto.BeerDto;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {

	public BeerDto beerToBeerDto(Beer beer);
	public Beer beerDtoToBeer(BeerDto beerDto);
	public BeerDto beerToBeerDto(Beer beer, boolean showInventoryOnHand);
	
}
