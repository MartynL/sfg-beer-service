package com.mlatta.beer.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import com.mlatta.beer.model.enums.BeerStyle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerDto {

	private UUID id;
	private Integer version;
	private OffsetDateTime createdDate;
	private OffsetDateTime lastModified;
	private String beerName;
	private BeerStyle beerStyle;
	private Long upc;
	private BigDecimal price;
	private Integer quantityOnHand;
	
}
