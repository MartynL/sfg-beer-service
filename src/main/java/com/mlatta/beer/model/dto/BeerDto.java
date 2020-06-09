package com.mlatta.beer.model.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
	private OffsetDateTime createdDate;
	
	@Null
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", shape = JsonFormat.Shape.STRING)
	private OffsetDateTime lastModified;
	
	@NotBlank
	@Size(min = 3, max = 100)
	private String beerName;
	
	@NotNull
	private BeerStyle beerStyle;
	
	@NotBlank
	private String upc;
	
	@NotNull
	@Positive
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private BigDecimal price;
	
	@Positive
	private Integer quantityOnHand;
	
}
