package com.mlatta.beer.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.net.URI;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.model.dto.BeerDto;
import com.mlatta.beer.model.mappers.BeerMapper;
import com.mlatta.beer.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {
	
	private final BeerMapper beerMapper;
	private final BeerRepository beerRepository;
	
	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId){
		BeerDto beer = beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElse(Beer.builder().build()));
		return ResponseEntity.ok(beer);
	}
	
	@PostMapping
	public ResponseEntity<BeerDto> saveNewBeer(@Validated @RequestBody BeerDto beerDto) {
		beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
		return ResponseEntity.created(URI.create("/api/v1/beer")).build();
	}

	@PutMapping("/{beerId}")
	@ResponseStatus(NO_CONTENT)
	public void updateBeerById(@PathVariable UUID beerId, @Validated @RequestBody BeerDto beerDto) {
		
		beerRepository
			.findById(beerId)
			.ifPresent(b -> {
				b.setBeerName(beerDto.getBeerName());
				b.setBeerStyle(beerDto.getBeerStyle().toString());
				b.setPrice(beerDto.getPrice());
				b.setUpc(beerDto.getUpc());
				
				beerRepository.save(b);
			});
		
	}
	
	@DeleteMapping("/{beerId}")
	@ResponseStatus(NO_CONTENT)
	public void deleteBeerById(@PathVariable UUID beerId) {
		beerRepository.deleteById(beerId);
	}
}
