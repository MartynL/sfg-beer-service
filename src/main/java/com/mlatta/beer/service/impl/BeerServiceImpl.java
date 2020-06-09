package com.mlatta.beer.service.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.exceptions.NotFoundException;
import com.mlatta.beer.model.dto.BeerDto;
import com.mlatta.beer.model.mappers.BeerMapper;
import com.mlatta.beer.repositories.BeerRepository;
import com.mlatta.beer.service.BeerService;

@Service("beerService")
public class BeerServiceImpl implements BeerService {

	private final BeerMapper beerMapper;
	private final BeerRepository beerRepository;
	
	public BeerServiceImpl(BeerMapper beerMapper, BeerRepository beerRepository) {
		this.beerMapper = beerMapper;
		this.beerRepository = beerRepository;
	}

	@Override
	public BeerDto getById(UUID beerId) throws NotFoundException {
		return beerMapper.beerToBeerDto(
			beerRepository.findById(beerId).orElseThrow(NotFoundException::new)
		);
	}

	@Override
	public BeerDto saveNewBeer(BeerDto beerDto) {
		Beer beer = beerMapper.beerDtoToBeer(beerDto);
		return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

	@Override
	public BeerDto updateBeer(UUID beerId, BeerDto beerDto) throws NotFoundException {
		
		Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);

		beer.setBeerName(beerDto.getBeerName());
		beer.setBeerStyle(beerDto.getBeerStyle().toString());
		beer.setPrice(beerDto.getPrice());
		beer.setUpc(beerDto.getUpc());
				
		return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

	@Override
	public void deleteBeer(UUID beerId) {
		beerRepository.deleteById(beerId);
	}

}
