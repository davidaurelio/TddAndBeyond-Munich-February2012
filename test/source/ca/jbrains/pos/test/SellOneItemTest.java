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

		public void setText(String text) {
			this.text = text;
		}
	}

	public static class Sale {
		private Display display;
		private final Map<String, String> pricesByBarcode;

		public Sale(Display display, Map<String, String> pricesByBarcode) {
			this.display = display;
			this.pricesByBarcode = pricesByBarcode;
		}

		public void onBarcode(String barcode) {
			if ("".equals(barcode))
				display.setText("Scanning error: empty barcode");
			else if (pricesByBarcode.containsKey(barcode))
				display.setText(pricesByBarcode.get(barcode));
			else
				display.setText("No product found for " + barcode);
		}
	}

	@Test
	public void productFound() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display, new HashMap<String, String>() {
			{
				put("12345", "EUR 7,95");
				put("23456", "EUR 12,50");
			}
		});

		sale.onBarcode("12345");

		assertEquals("EUR 7,95", display.getText());
	}

	@Test
	public void anotherProductFound() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display, new HashMap<String, String>() {
			{
				put("12345", "EUR 7,95");
				put("23456", "EUR 12,50");
			}
		});

		sale.onBarcode("23456");

		assertEquals("EUR 12,50", display.getText());
	}

	@Test
	public void productNotFound() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display, new HashMap<String, String>() {
			{
				put("12345", "EUR 7,95");
				put("23456", "EUR 12,50");
			}
		});

		sale.onBarcode("99999");

		assertEquals("No product found for 99999", display.getText());
	}
	@Test
	public void emptyBarcode() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display, new HashMap<String, String>() {
			{
				put("12345", "EUR 7,95");
				put("23456", "EUR 12,50");
			}
		});
		
		sale.onBarcode("");
		
		assertEquals("Scanning error: empty barcode", display.getText());
	}
}
