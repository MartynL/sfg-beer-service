package com.mlatta.beer.controller;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.model.dto.BeerDto;
import com.mlatta.beer.model.enums.BeerStyle;
import com.mlatta.beer.repositories.BeerRepository;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
@ComponentScan("com.mlatta.beer.model.mappers")
public class BeerControllerTest {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;
	
	@MockBean BeerRepository beerRepository;

	@Test
	void testGetBeerById() throws Exception {
		
		when(beerRepository.findById(any())).thenReturn(of(Beer.builder().build()));
		
		mockMvc
			.perform(get("/api/v1/beer/" + UUID.randomUUID().toString()).accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	void testSaveNewBeer() throws Exception {
		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		
		mockMvc
			.perform(post("/api/v1/beer").contentType(MediaType.APPLICATION_JSON).content(beerDtoJson))
			.andExpect(status().isCreated());
	}

	@Test
	void testUpdateBeerById() throws Exception {
		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		
		mockMvc
			.perform(put("/api/v1/beer/" + UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON).content(beerDtoJson))
			.andExpect(status().isNoContent());
	}
	
	private BeerDto getValidBeerDto() {
		return BeerDto
				.builder()
				.beerName("Test Beer")
				.beerStyle(BeerStyle.ALE)
				.price(new BigDecimal("2.99"))
				.upc(123456789L)
				.build();
	}

}
