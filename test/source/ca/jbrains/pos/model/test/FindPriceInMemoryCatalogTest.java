package ca.jbrains.pos.model.test;


import java.util.Collections;
import java.util.Map;


import ca.jbrains.pos.controller.test.SellOneItemTest.Catalog;
import ca.jbrains.pos.controller.test.SellOneItemTest.Price;

public class FindPriceInMemoryCatalogTest extends FindPriceInCatalogContract {
	public static class InMemoryCatalog implements Catalog {
		private final Map<String, Price> pricesByBarcode;

		public InMemoryCatalog(Map<String, Price> pricesByBarcode) {
			this.pricesByBarcode = pricesByBarcode;
		}

		public Price findPrice(String barcode) {
			return pricesByBarcode.get(barcode);
		}
	}

	@Override
	public Catalog createCatalogWith(String barcode, Price price) {
		return new InMemoryCatalog(Collections.<String, Price> singletonMap(
				barcode, price));
	}

	@Override
	public Catalog createCatalogWithout(String barcodeToAvoid) {
		return new InMemoryCatalog(Collections.<String, Price> singletonMap(
				"not " + barcodeToAvoid, anyPrice()));
	}

	private Price anyPrice() {
		return Price.euroCents(1000000);
	}
}
