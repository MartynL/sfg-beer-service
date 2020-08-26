package com.mlatta.beer.inventory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mlatta.beer.bootstrap.BeerLoader;
import com.mlatta.beer.service.inventory.BeerInventoryService;

@Disabled
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {

	@Autowired private BeerInventoryService beerInventoryService;
	
	@Test
	void test() {
		Integer qoh = beerInventoryService.getOnHandInventory(UUID.fromString(BeerLoader.BEER_1_UPC));
		System.out.println(qoh);
		assertNotNull(qoh);
	}

}
