package com.foo.shoppingcart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Currency;

import org.junit.Test;

public class MoneyTest {
	@Test
	public void testAdd() {
		assertEquals("AUD 10.00", new Money(Currency.getInstance("AUD"), 5.35)
				.add(new Money(Currency.getInstance("AUD"), 4.65)).toString());
		assertEquals("AUD 4.65", Money.EMPTY_MONEY.add(new Money(Currency.getInstance("AUD"), 4.65)).toString());
		try {
			Money.EMPTY_MONEY.add(Money.EMPTY_MONEY);
			fail();
		} catch (AssertionError e) {
		}
		try {
			new Money(Currency.getInstance("AUD"), 5.35).add(Money.EMPTY_MONEY);
			fail();
		} catch (AssertionError e) {
		}
		try {
			new Money(Currency.getInstance("AUD"), 5.35).add(new Money(Currency.getInstance("PHP"), 4.65));
			fail();
		} catch (AssertionError e) {
		}
	}

	@Test
	public void testSubtract() {
		assertEquals("AUD 10.00", new Money(Currency.getInstance("AUD"), 15.35)
				.subtract(new Money(Currency.getInstance("AUD"), 5.35)).toString());
		assertEquals("AUD 5.35", new Money(Currency.getInstance("AUD"), 5.35).subtract(Money.EMPTY_MONEY).toString());
		try {
			Money.EMPTY_MONEY.subtract(Money.EMPTY_MONEY);
			fail();
		} catch (AssertionError e) {
		}
		try {
			Money.EMPTY_MONEY.subtract(new Money(Currency.getInstance("AUD"), 4.65));
			fail();
		} catch (AssertionError e) {
		}
		try {
			new Money(Currency.getInstance("USD"), 15.35).subtract(new Money(Currency.getInstance("AUD"), 5.35));
			fail();
		} catch (AssertionError e) {
		}
	}

	@Test
	public void testMultiply() {
		assertEquals("AUD 33.33", new Money(Currency.getInstance("AUD"), 11.11).multiply(3).toString());
	}
}
