package com.mlatta.beer.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.net.URI;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mlatta.beer.exceptions.NotFoundException;
import com.mlatta.beer.model.BeerPagedList;
import com.mlatta.beer.model.dto.BeerDto;
import com.mlatta.beer.model.enums.BeerStyle;
import com.mlatta.beer.service.BeerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
public class BeerController {
	
	private static final Integer DEFAULT_PAGE_SIZE = 25;
	private static final Integer DEFAULT_PAGE_NUMBER = 0;
	
	private final BeerService beerService;
	
	@GetMapping(produces="application/json")
	public ResponseEntity<BeerPagedList> listBeers(
			@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", required = false) Integer pageSize,
			@RequestParam(value = "beerName", required = false) String beerName,
			@RequestParam(value = "beerStyle", required = false) BeerStyle beerStyle,
			@RequestParam(value = "showInventoryOnHand", defaultValue = "false") boolean showInventoryOnHand) {
		
		if(pageNumber == null || pageNumber < 0) {
			pageNumber = DEFAULT_PAGE_NUMBER;
		}

		if(pageSize == null || pageSize < 0) {
			pageSize = DEFAULT_PAGE_SIZE;
		}
		
		BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize), showInventoryOnHand);
		
		return ResponseEntity.ok(beerList);
	}
	
	
	@GetMapping("/{beerId}")
	public ResponseEntity<BeerDto> getBeerById(@PathVariable UUID beerId,
			@RequestParam(value = "showInventoryOnHand", defaultValue = "false") boolean showInventoryOnHand){
		try {
			return ResponseEntity.ok(beerService.getById(beerId, showInventoryOnHand));
		} catch (NotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	public ResponseEntity<BeerDto> saveNewBeer(@Validated @RequestBody BeerDto beerDto) {
		BeerDto savedBeer = beerService.saveNewBeer(beerDto);
		return ResponseEntity.created(URI.create("/api/v1/beer/" + savedBeer.getId())).build();
	}

	@PutMapping("/{beerId}")
	@ResponseStatus(NO_CONTENT)
	public void updateBeerById(@PathVariable UUID beerId, @Validated @RequestBody BeerDto beerDto) throws NotFoundException {
		beerService.updateBeer(beerId, beerDto);
	}
	
	@DeleteMapping("/{beerId}")
	@ResponseStatus(NO_CONTENT)
	public void deleteBeerById(@PathVariable UUID beerId) {
		beerService.deleteBeer(beerId);
	}
}
