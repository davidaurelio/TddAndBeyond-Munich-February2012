package ca.jbrains.pos.test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
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

		public Sale(Display display) {
			this.display = display;
		}

		public void onBarcode(String barcode) {
			if ("12345".equals(barcode))
				display.setText("EUR 7,95");
			else if ("23456".equals(barcode))
				display.setText("EUR 12,50");
			else
				display.setText("No product found for " + barcode);
		}
	}

	@Test
	public void productFound() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display);

		sale.onBarcode("12345");

		assertEquals("EUR 7,95", display.getText());
	}

	@Test
	public void anotherProductFound() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display);

		sale.onBarcode("23456");

		assertEquals("EUR 12,50", display.getText());
	}

	@Test
	public void productNotFound() throws Exception {
		Display display = new Display();
		Sale sale = new Sale(display);

		sale.onBarcode("99999");

		assertEquals("No product found for 99999", display.getText());
	}
}
