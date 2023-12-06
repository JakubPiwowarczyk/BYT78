package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class MoneyTest {
	Currency SEK, DKK, NOK, EUR;
	Money SEK100, EUR10, SEK200, EUR20, SEK0, EUR0, SEKn100;
	
	@Before
	public void setUp() throws Exception {
		SEK = new Currency("SEK", 0.15);
		DKK = new Currency("DKK", 0.20);
		EUR = new Currency("EUR", 1.5);
		SEK100 = new Money(10000, SEK);
		EUR10 = new Money(1000, EUR);
		SEK200 = new Money(20000, SEK);
		EUR20 = new Money(2000, EUR);
		SEK0 = new Money(0, SEK);
		EUR0 = new Money(0, EUR);
		SEKn100 = new Money(-10000, SEK);
	}

	@Test
	public void testGetAmount() {
		Money money = new Money(10000, new Currency("EUR", 1.00));
		assertEquals(100, money.getAmount(), 0.01);
	}

	@Test
	public void testGetCurrency() {
		Currency currency = new Currency("EUR", 1.00);
		Money money = new Money(10000, currency);
		assertEquals(currency, money.getCurrency());
	}

	@Test
	public void testToString() {
		Currency currency = new Currency("EUR", 1.00);
		Money money = new Money(10050, currency);
		assertEquals("100.5 EUR", money.toString());
	}

	@Test
	public void testGlobalValue() {
		Currency currency = new Currency("EUR", 0.75);
		Money money = new Money(10000, currency);
		assertEquals(7500, money.universalValue(), 0.01);
	}

	@Test
	public void testEqualsMoney() {
		Currency currency = new Currency("EUR", 1.00);
		Money money1 = new Money(10000, currency);
		Money money2 = new Money(10000, currency);
		assertTrue(money1.equals(money2));
	}

	@Test
	public void testAdd() {
		Currency currency = new Currency("EUR", 1.00);
		Money money1 = new Money(10000, currency);
		Money money2 = new Money(10000, currency);
		Money result = money1.add(money2);
		assertEquals("200.0 EUR", result.toString());
	}

	@Test
	public void testSub() {
		Currency currency = new Currency("EUR", 1.00);
		Money money1 = new Money(20000, currency);
		Money money2 = new Money(10000, currency);
		Money result = money1.sub(money2);
		assertEquals("100.0 EUR", result.toString());
	}

	@Test
	public void testIsZero() {
		Currency currency = new Currency("EUR", 1.00);
		Money money = new Money(0, currency);
		assertTrue(money.isZero());
	}

	@Test
	public void testNegate() {
		Currency currency = new Currency("EUR", 1.00);
		Money money = new Money(10000, currency);
		Money negatedMoney = money.negate();
		assertEquals(-100.0, negatedMoney.getAmount(), 0.01);
	}

	@Test
	public void testCompareTo() {
		Currency currency = new Currency("EUR", 1.00);
		Money money1 = new Money(10000, currency);
		Money money2 = new Money(5000, currency);
		assertTrue(money1.compareTo(money2) > 0);
		assertTrue(money2.compareTo(money1) < 0);
		assertEquals(0, money1.compareTo(money1));
	}
}
