package com.mlatta.beer.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BeerLoader implements CommandLineRunner {

	private final BeerRepository beerRepository;
	
	@Override
	public void run(String... args) throws Exception {
		loadBeerObjects();
	}

	private void loadBeerObjects() {
		if(beerRepository.count() == 0) {
			beerRepository.save(
					Beer.builder()
					.beerName("Trade Winds")
					.beerStyle("IPA")
					.quantityToBrew(200)
					.minOnHand(12)
					.upc(13135135135L)
					.price(new BigDecimal("4.50"))
					.build());
			
			beerRepository.save(
					Beer.builder()
					.beerName("Heineken")
					.beerStyle("LAGER")
					.quantityToBrew(400)
					.minOnHand(20)
					.upc(13135135133L)
					.price(new BigDecimal("3.90"))
					.build());
			
		}
	}
	

}
