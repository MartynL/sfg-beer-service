package com.mlatta.beer.events;

import java.io.Serializable;

import com.mlatta.beer.model.dto.BeerDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerEvent implements Serializable {
	
	private static final long serialVersionUID = 6719278305683923459L;

	private BeerDto beerDto;

}
