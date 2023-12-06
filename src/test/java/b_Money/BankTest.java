package b_Money;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BankTest {
	Currency SEK, DKK;
	Bank SweBank, Nordea, DanskeBank;
	
	@Before
	public void setUp() throws Exception {
		DKK = new Currency("DKK", 0.20);
		SEK = new Currency("SEK", 0.15);
		SweBank = new Bank("SweBank", SEK);
		Nordea = new Bank("Nordea", SEK);
		DanskeBank = new Bank("DanskeBank", DKK);
		SweBank.openAccount("Ulrika");
		SweBank.openAccount("Bob");
		Nordea.openAccount("Bob");
		DanskeBank.openAccount("Gertrud");
	}

	@Test
	public void testGetName() {
		assertEquals("SweBank", SweBank.getName());
	}

	@Test
	public void testGetCurrency() {
		assertEquals(SEK, SweBank.getCurrency());
	}

	@Test
	public void testOpenAccount() throws AccountExistsException, AccountDoesNotExistException {
		try {
			SweBank.openAccount("Charlie");
			assertNotNull(SweBank.getAccountlist().get("Charlie"));
		} catch (AccountExistsException e) {
			fail("Failed to open a new account");
		}

	}

	@Test
	public void testDeposit() throws AccountDoesNotExistException {
        try {
            SweBank.openAccount("Alice");
        } catch (AccountExistsException e) {
            throw new RuntimeException(e);
        }
        SweBank.deposit("Alice", new Money(50000, SEK));
		assertEquals(new Money(50000, SEK), SweBank.getAccountlist().get("Alice").getBalance());
	}

	@Test
	public void testWithdraw() throws AccountDoesNotExistException {
        try {
            SweBank.openAccount("Alice");
        } catch (AccountExistsException e) {
            throw new RuntimeException(e);
        }
        SweBank.deposit("Alice", new Money(100000, SEK));
		SweBank.withdraw("Alice", new Money(50000, SEK));
		assertEquals(new Money(50000, SEK), SweBank.getAccountlist().get("Alice").getBalance());
	}
	
	@Test
	public void testGetBalance() throws AccountDoesNotExistException {
        try {
            SweBank.openAccount("Alice");
        } catch (AccountExistsException e) {
            throw new RuntimeException(e);
        }
        SweBank.deposit("Alice", new Money(100000, SEK));
		assertEquals(100000, SweBank.getBalance("Alice"), 0.01);
	}
	
	@Test
	public void testTransfer() throws AccountDoesNotExistException {
        try {
            SweBank.openAccount("Alice");
			SweBank.openAccount("Bob");
        } catch (AccountExistsException e) {
            throw new RuntimeException(e);
        }

		SweBank.deposit("Alice", new Money(100000, SEK));
		SweBank.transfer("Alice", SweBank, "Bob", new Money(50000, SEK));

		assertEquals(new Money(50000, SEK), SweBank.getAccountlist().get("Alice").getBalance());
		assertEquals(new Money(50000, SEK), SweBank.getAccountlist().get("Bob").getBalance());
	}
	
	@Test
	public void testTimedPayment() throws AccountDoesNotExistException {
		try {
			SweBank.openAccount("Alice");
			SweBank.openAccount("Bob");
		} catch (AccountExistsException e) {
			throw new RuntimeException(e);
		}

		SweBank.deposit("Alice", new Money(100000, SEK));
		SweBank.addTimedPayment("Alice", "payment1", 2, 1, new Money(500, SEK), SweBank, "Bob");

		SweBank.tick();

		assertEquals(new Money(99500, SEK), SweBank.getAccountlist().get("Alice").getBalance());
		assertEquals(new Money(500, SEK), SweBank.getAccountlist().get("Bob").getBalance());
	}
}
