package com.mlatta.beer.events;

import com.mlatta.beer.model.dto.BeerDto;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NewInventoryEvent extends BeerEvent {

	private static final long serialVersionUID = 7059750864041708182L;
	
	public NewInventoryEvent(BeerDto dto) {
		super(dto);
	}

}
