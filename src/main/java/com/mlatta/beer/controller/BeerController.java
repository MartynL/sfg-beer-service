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

import com.mlatta.beer.model.dto.BeerDto;

@RestController
@RequestMapping("/api/v1/beer")
public class BeerController {
	
	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId){
		//TODO impl
		return ResponseEntity.ok(BeerDto.builder().build());
	}
	
	@PostMapping
	public ResponseEntity<BeerDto> saveNewBeer(@Validated @RequestBody BeerDto beerDto) {
		//TODO impl 
		return ResponseEntity.created(URI.create("/api/v1/beer" + UUID.randomUUID())).build();
	}

	@PutMapping("/{beerId}")
	@ResponseStatus(NO_CONTENT)
	public void updateBeerById(@PathVariable UUID beerId, @Validated @RequestBody BeerDto beerDto) {
		//TODO impl
	}
	
	@DeleteMapping("/{beerId}")
	@ResponseStatus(NO_CONTENT)
	public void deleteBeerById(@PathVariable UUID beerId) {
		//TODO impl
	}
}
