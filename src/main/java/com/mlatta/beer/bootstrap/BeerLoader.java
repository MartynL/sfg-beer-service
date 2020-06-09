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

	public static final String BEER_1_UPC = "062928889569212";
	public static final String BEER_2_UPC = "062977355469234";
	public static final String BEER_3_UPC = "052924582469256";
	
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
					.upc(BEER_1_UPC)
					.price(new BigDecimal("4.50"))
					.build());
			
			beerRepository.save(
					Beer.builder()
					.beerName("Heineken")
					.beerStyle("LAGER")
					.quantityToBrew(400)
					.minOnHand(20)
					.upc(BEER_2_UPC)
					.price(new BigDecimal("3.90"))
					.build());
			
			beerRepository.save(
					Beer.builder()
					.beerName("Stella")
					.beerStyle("LAGER")
					.quantityToBrew(200)
					.minOnHand(20)
					.upc(BEER_3_UPC)
					.price(new BigDecimal("4.10"))
					.build());
			
		}
	}
	

}
