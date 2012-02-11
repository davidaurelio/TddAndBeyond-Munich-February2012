package ca.jbrains.pos.controller.test;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class SellOneItemTest {
	public static class SaleController {
		private final Display display;
		private final Catalog catalog;

		public SaleController(Catalog catalog, Display display) {
			this.catalog = catalog;
			this.display = display;
		}

		public void onBarcode(String barcode) {
			if ("".equals(barcode)) {
				display.displayScannedEmptyBarcodeMessage();
				return;
			}

			Price price = catalog.findPrice(barcode);
			if (price == null)
				display.displayProductNotFoundMessage(barcode);
			else
				display.displayPrice(price);
		}
	}

	public static class Price {
		private final int euroCents;

		public Price(int euroCents) {
			this.euroCents = euroCents;
		}

		public static Price euroCents(int euroCents) {
			return new Price(euroCents);
		}

		@Override
		public boolean equals(Object other) {
			if (other instanceof Price) {
				Price that = (Price) other;
				return this.euroCents == that.euroCents;
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return euroCents;
		}

		@Override
		public String toString() {
			return "�" + getEuro();
		}

		public double getEuro() {
			return euroCents / 100.0d;
		}
	}

	public interface Display {
		void displayPrice(Price price);

		void displayProductNotFoundMessage(String barcode);

		void displayScannedEmptyBarcodeMessage();
	}

	public interface Catalog {
		Price findPrice(String barcode);
	}

	private Mockery mockery = new Mockery();

	@Test
	public void productFound() throws Exception {
		final Price price = Price.euroCents(795);

		final Catalog catalog = mockery.mock(Catalog.class);
		final Display display = mockery.mock(Display.class);

		mockery.checking(new Expectations() {
			{
				allowing(catalog).findPrice(with("12345"));
				will(returnValue(price));

				oneOf(display).displayPrice(with(price));
			}
		});

		SaleController saleController = new SaleController(catalog, display);
		saleController.onBarcode("12345");
	}

	@Test
	public void productNotFound() throws Exception {
		final Catalog catalog = mockery.mock(Catalog.class);
		final Display display = mockery.mock(Display.class);

		mockery.checking(new Expectations() {
			{
				allowing(catalog).findPrice(with("12345"));
				will(returnValue(null));

				oneOf(display).displayProductNotFoundMessage(with("12345"));
			}
		});

		SaleController saleController = new SaleController(catalog, display);
		saleController.onBarcode("12345");
	}

	@Test
	public void emptyBarcode() throws Exception {
		final Catalog catalog = mockery.mock(Catalog.class);
		final Display display = mockery.mock(Display.class);

		mockery.checking(new Expectations() {
			{
				ignoring(catalog);

				oneOf(display).displayScannedEmptyBarcodeMessage();
			}
		});

		SaleController saleController = new SaleController(catalog, display);
		saleController.onBarcode("");
	}
}
