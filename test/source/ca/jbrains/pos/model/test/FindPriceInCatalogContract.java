package ca.jbrains.pos.model.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ca.jbrains.pos.controller.test.SellOneItemTest.Catalog;
import ca.jbrains.pos.controller.test.SellOneItemTest.Price;

public abstract class FindPriceInCatalogContract {
	public abstract Catalog createCatalogWithout(String barcodeToAvoid);

	public abstract Catalog createCatalogWith(String barcode, Price price);

	@Test
	public void productFound() throws Exception {
		Catalog catalog = createCatalogWith("12345", Price.euroCents(795));
	
		assertEquals(Price.euroCents(795), catalog.findPrice("12345"));
	}

	@Test
	public void productNotFound() throws Exception {
		Catalog catalog = createCatalogWithout("12345");
	
		assertEquals(null, catalog.findPrice("12345"));
	}

}