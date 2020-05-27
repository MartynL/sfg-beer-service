package com.mlatta.beer.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.mlatta.beer.domain.Beer;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
