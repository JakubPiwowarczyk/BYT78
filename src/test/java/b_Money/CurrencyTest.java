package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CurrencyTest {
	Currency SEK, DKK, NOK, EUR;
	
	@Before
	public void setUp() throws Exception {
		/* Setup currencies with exchange rates */
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
	}

	@Test
	public void testGetName() {
		Currency currency = new Currency("USD", 1.0);
		assertEquals("USD", currency.getName());
	}
	
	@Test
	public void testGetRate() {
		Currency currency = new Currency("EUR", 0.85);
		assertEquals(0.85, currency.getRate(), 0.01);
	}
	
	@Test
	public void testSetRate() {
		Currency currency = new Currency("GBP", 1.25);
		currency.setRate(1.30);
		assertEquals(1.30, currency.getRate(), 0.01);
	}
	
	@Test
	public void testGlobalValue() {
		Currency currency = new Currency("JPY", 110.0);
		double globalValue = currency.universalValue(100);
		assertEquals(11000.0, globalValue, 0.01);
	}
	
	@Test
	public void testValueInThisCurrency() {
		Currency usdCurrency = new Currency("USD", 0.85);
		Currency eurCurrency = new Currency("EUR", 1.00);
		int amountInEur = eurCurrency.valueInThisCurrency(100, usdCurrency);
		assertEquals(85, amountInEur);
	}

}
