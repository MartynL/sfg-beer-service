package com.mlatta.beer.service.inventory;

import java.util.UUID;

public interface BeerInventoryService {

	public Integer getOnHandInventory(UUID beerId);
}
