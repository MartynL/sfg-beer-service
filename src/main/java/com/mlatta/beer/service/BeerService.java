package com.mlatta.beer.service;

import java.util.UUID;

import com.mlatta.beer.exceptions.NotFoundException;
import com.mlatta.beer.model.dto.BeerDto;

public interface BeerService {

	public BeerDto getById(UUID beerId) throws NotFoundException;

	public BeerDto saveNewBeer(BeerDto beerDto);

	public BeerDto updateBeer(UUID beerId, BeerDto beerDto) throws NotFoundException;

	public void deleteBeer(UUID beerId);

}
