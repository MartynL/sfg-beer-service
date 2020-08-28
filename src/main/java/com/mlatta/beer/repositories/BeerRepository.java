package com.mlatta.beer.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.model.enums.BeerStyle;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

	Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyle beerStyle, Pageable pageRequest);

	Page<Beer> findAllByBeerName(String beerName, Pageable pageRequest);

	Page<Beer> findAllByBeerStyle(BeerStyle beerStyle, Pageable pageRequest);

	Optional<Beer> findByUpc(String upc);

}
