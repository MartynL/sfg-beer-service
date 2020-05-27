package com.mlatta.beer.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;

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

	@Null
	private UUID id;
	
	@Null
	private Integer version;
	
	@Null
	private OffsetDateTime createdDate;
	
	@Null
	private OffsetDateTime lastModified;
	
	@NotBlank
	private String beerName;
	
	@NotNull
	private BeerStyle beerStyle;
	
	@NotNull
	@Positive
	private Long upc;
	
	@NotNull
	@Positive
	private BigDecimal price;
	private Integer quantityOnHand;
	
}
