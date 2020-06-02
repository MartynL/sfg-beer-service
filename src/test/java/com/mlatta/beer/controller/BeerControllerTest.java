package com.mlatta.beer.controller;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.util.StringUtils.collectionToDelimitedString;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mlatta.beer.domain.Beer;
import com.mlatta.beer.model.dto.BeerDto;
import com.mlatta.beer.model.enums.BeerStyle;
import com.mlatta.beer.repositories.BeerRepository;

@WebMvcTest
@ComponentScan("com.mlatta.beer.model.mappers")
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.mlatta.example.com", uriPort = 80)
@ExtendWith({MockitoExtension.class, RestDocumentationExtension.class})
public class BeerControllerTest {

	@Autowired MockMvc mockMvc;
	@Autowired ObjectMapper objectMapper;
	
	@MockBean BeerRepository beerRepository;

	@Test
	public void testGetBeerById() throws Exception {
		
		when(beerRepository.findById(any())).thenReturn(of(Beer.builder().build()));
		
		mockMvc
			.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
				.param("isCold", "yes")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andDo(document("v1/beer/get", 
				pathParameters(
					parameterWithName("beerId").description("UUID of desired beer to get.")
				),
				requestParameters(
					parameterWithName("isCold").description("temp state of beer")
				),
				responseFields(
					fieldWithPath("id").description("Id of beer"),
					fieldWithPath("version").description("Version Number"),
					fieldWithPath("createdDate").description("Date Created"),
					fieldWithPath("lastModified").description("Date Updated"),
					fieldWithPath("beerName").description("Name of the beer"),
					fieldWithPath("beerStyle").description("Style of beer"),
					fieldWithPath("upc").description("UPC of beer"),
					fieldWithPath("price").description("Price of beer"),
					fieldWithPath("quantityOnHand").description("Quantity of beer on hand")
				)
			));
	}

	@Test
	public void testSaveNewBeer() throws Exception {
		BeerDto beerDto = getValidBeerDto();
		String beerDtoJson = objectMapper.writeValueAsString(beerDto);
		
		ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
		
		mockMvc
			.perform(post("/api/v1/beer")
					.contentType(MediaType.APPLICATION_JSON)
					.content(beerDtoJson))
			.andExpect(status().isCreated())
			.andDo(document("v1/beer/post",
					requestFields(
							fields.withPath("id").ignored(),
							fields.withPath("version").ignored(),
							fields.withPath("createdDate").ignored(),
							fields.withPath("lastModified").ignored(),
							fields.withPath("beerName").description("Name of the beer"),
							fields.withPath("beerStyle").description("Style of beer"),
							fields.withPath("upc").description("UPC of beer").attributes(),
							fields.withPath("price").description("Price of beer"),
							fields.withPath("quantityOnHand").ignored()
					)
			));
	}

	@Test
	public void testUpdateBeerById() throws Exception {
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

	private static class ConstrainedFields {
		
		private final ConstraintDescriptions constraintDescriptions;
		
		ConstrainedFields(Class<?> input) {
			this.constraintDescriptions = new ConstraintDescriptions(input);
		}
		
		private FieldDescriptor withPath(String path) {
			return fieldWithPath(path)
					.attributes(key("constraints")
							.value(collectionToDelimitedString(this.constraintDescriptions
									.descriptionsForProperty(path),". ")));
		}
	}
}
