package ca.jbrains.pos.model.test;

import java.io.Reader;
import java.io.StringReader;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

import ca.jbrains.pos.controller.test.SellOneItemTest.Catalog;
import ca.jbrains.pos.controller.test.SellOneItemTest.Price;

import com.google.common.base.Joiner;

public class FindPriceInCsvCatalogTest extends FindPriceInCatalogContract {
	public static class CsvCatalog implements Catalog {
		public CsvCatalog(Reader reader) {
		}

		@Override
		public Price findPrice(String barcode) {
			return null;
		}
	}

	@Override
	public Catalog createCatalogWithout(String barcodeToAvoid) {
		return new CsvCatalog(
				new StringReader(Joiner
						.on(System.getProperty("line.separator")).join(
								Arrays.asList(
										"barcode, price",
										"not " + barcodeToAvoid
												+ ", \"EUR 7,95\"").iterator())));
	}

	@Override
	public Catalog createCatalogWith(String barcode, Price price) {
		return new CsvCatalog(new StringReader(Joiner.on(
				System.getProperty("line.separator")).join(
				Arrays.asList("barcode, price",
						barcode + ", \"" + format(price) + "\"").iterator())));
	}

	private static String format(Price price) {
		return "EUR "
				+ NumberFormat.getNumberInstance(Locale.GERMANY).format(
						price.getEuro());
	}

}
