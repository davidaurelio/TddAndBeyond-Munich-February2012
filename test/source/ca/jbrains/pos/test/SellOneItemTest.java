package ca.jbrains.pos.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class SellOneItemTest {
	public static class Display {
		private String text;

		public String getText() {
			return text;
		}

		public void displayPrice(String price) {
			this.text = price;
		}

		public void displayProductNotFoundMessage(String barcode) {
			this.text = "No product found for " + barcode;
		}

		public void displayScanEmptyBarcodeMessage() {
			this.text = "Scanning error: empty barcode";
		}
	}

	public static class Sale {
		private final Display display;
		private final Catalog catalog;

		public Sale(Display display, Catalog catalog) {
			this.display = display;
			this.catalog = catalog;
		}

		public void onBarcode(String barcode) {
			if ("".equals(barcode))
				display.displayScanEmptyBarcodeMessage();
			else if (catalog.hasBarcode(barcode))
				display.displayPrice(catalog.findPrice(barcode));
			else
				display.displayProductNotFoundMessage(barcode);
		}
	}

	@Test
	public void productFound() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display, new Catalog(new HashMap<String, String>() {
			{
				put("12345", "EUR 7,95");
				put("23456", "EUR 12,50");
			}
		}));

		sale.onBarcode("12345");

		assertEquals("EUR 7,95", display.getText());
	}

	@Test
	public void anotherProductFound() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display, new Catalog(new HashMap<String, String>() {
			{
				put("12345", "EUR 7,95");
				put("23456", "EUR 12,50");
			}
		}));

		sale.onBarcode("23456");

		assertEquals("EUR 12,50", display.getText());
	}

	@Test
	public void productNotFound() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display, new Catalog(new HashMap<String, String>() {
			{
				put("12345", "EUR 7,95");
				put("23456", "EUR 12,50");
			}
		}));

		sale.onBarcode("99999");

		assertEquals("No product found for 99999", display.getText());
	}

	@Test
	public void emptyBarcode() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display, new Catalog(new HashMap<String, String>() {
			{
				put("12345", "EUR 7,95");
				put("23456", "EUR 12,50");
			}
		}));

		sale.onBarcode("");

		assertEquals("Scanning error: empty barcode", display.getText());
	}
}
