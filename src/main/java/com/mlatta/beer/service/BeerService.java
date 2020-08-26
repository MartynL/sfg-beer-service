package com.mlatta.beer.service;

import java.util.UUID;

import org.springframework.data.domain.PageRequest;

import com.mlatta.beer.exceptions.NotFoundException;
import com.mlatta.beer.model.BeerPagedList;
import com.mlatta.beer.model.dto.BeerDto;
import com.mlatta.beer.model.enums.BeerStyle;

public interface BeerService {

	public BeerDto getById(UUID beerId) throws NotFoundException;

	public BeerDto saveNewBeer(BeerDto beerDto);

	public BeerDto updateBeer(UUID beerId, BeerDto beerDto) throws NotFoundException;

	public void deleteBeer(UUID beerId);

	public BeerPagedList listBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest);

}
