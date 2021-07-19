package com.mlatta.beer.events;

import com.mlatta.beer.model.dto.BeerDto;

public class BrewBeerEvent extends BeerEvent {

	private static final long serialVersionUID = 7553581187997519474L;

	public BrewBeerEvent(BeerDto dto) {
		super(dto);
	}
}
