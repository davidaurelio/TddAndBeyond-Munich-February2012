package ca.jbrains.pos.model.test;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;

import ca.jbrains.pos.controller.test.SellOneItemTest.Catalog;
import ca.jbrains.pos.controller.test.SellOneItemTest.Price;

public class FindPriceInMemoryCatalogTest {
	public static class InMemoryCatalog implements Catalog {
		private final Map<String, Price> pricesByBarcode;

		public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
			this.pricesByBarcode = pricesByBarcode;
		}

		public Price findPrice(String barcode) {
			return pricesByBarcode.get(barcode);
		}
	}

	@Test
	public void productFound() throws Exception {
		InMemoryCatalog catalog = new InMemoryCatalog(
				Collections.<String, Price> singletonMap("12345",
						Price.euroCents(795)));

		assertEquals(Price.euroCents(795), catalog.findPrice("12345"));
	}

	@Test
	public void productNotFound() throws Exception {
		InMemoryCatalog catalog = new InMemoryCatalog(
				Collections.<String, Price> emptyMap());

		assertEquals(null, catalog.findPrice("12345"));
	}
}
