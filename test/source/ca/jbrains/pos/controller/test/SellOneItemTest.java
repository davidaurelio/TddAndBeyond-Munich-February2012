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
			display.displayPrice(catalog.findPrice(barcode));
		}
	}

	public static class Price {
		public static Price euroCents(int euroCents) {
			return new Price();
		}
		
		@Override
		public String toString() {
			return "a Price";
		}
	}

	public interface Display {
		void displayPrice(Price price);
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
}
