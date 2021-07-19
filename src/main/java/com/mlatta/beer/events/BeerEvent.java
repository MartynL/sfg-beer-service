package com.mlatta.beer.events;

import java.io.Serializable;

import com.mlatta.beer.model.dto.BeerDto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
public class BeerEvent implements Serializable {
	
	private static final long serialVersionUID = 6719278305683923459L;

	private final BeerDto beerDto;

}
