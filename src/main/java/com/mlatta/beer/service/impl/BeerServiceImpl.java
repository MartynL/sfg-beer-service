package com.mlatta.beer.service.impl;

import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.exceptions.NotFoundException;
import com.mlatta.beer.model.BeerPagedList;
import com.mlatta.beer.model.dto.BeerDto;
import com.mlatta.beer.model.enums.BeerStyle;
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
	@Cacheable(cacheNames = "beerCache", key="#beerId", condition="#showInventoryOnHand == false")
	public BeerDto getById(UUID beerId, boolean showInventoryOnHand) throws NotFoundException {
		Beer beer = beerRepository.findById(beerId).orElseThrow(NotFoundException::new);
		return beerMapper.beerToBeerDto(beer, showInventoryOnHand);
	}

	@Override
	@Cacheable(cacheNames = "beerUpcCache")
	public BeerDto getByUpc(String upc) throws NotFoundException {
		Beer beer = beerRepository.findByUpc(upc).orElseThrow(NotFoundException::new);
		return beerMapper.beerToBeerDto(beer);
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

	@Override
	@Cacheable(cacheNames = "beerListCache", condition="#showInventoryOnHand == false")
	public BeerPagedList listBeers(String beerName, BeerStyle beerStyle, PageRequest pageRequest, boolean showInventoryOnHand) {
		
		Page<Beer> beerPage;
		
		if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
		} else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
		} else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
			beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
		} else {
			beerPage = beerRepository.findAll(pageRequest);
		}
		
		return new BeerPagedList(
				beerPage
					.getContent()
					.stream()
					.map(t -> beerMapper.beerToBeerDto(t, showInventoryOnHand))
					.collect(Collectors.toList()),
				PageRequest
					.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
				beerPage.getTotalElements());
	}

}
