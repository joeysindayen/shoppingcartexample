package com.foo.shoppingcart;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

public class Money {
	private static final RoundingMode DEFAULT_MONEY_ROUNDINGMODE = RoundingMode.HALF_UP;
	private static final int DEFAULT_MONEY_SCALE = 2;

	public static final Money EMPTY_MONEY = new Money(null, BigDecimal.ZERO);

	private Currency currency;
	private BigDecimal amount;

	public Money(Currency currency, BigDecimal amount) {
		super();
		this.currency = currency;
		this.amount = amount;
	}

	public Money(Currency currency, double amount) {
		this(currency, createAmount(amount));
	}

	private static BigDecimal createAmount(double amount) {
		return setScaleRoundingMode(new BigDecimal(amount));
	}

	public Money subtract(Money subtrahend) {
		assert ((EMPTY_MONEY == subtrahend && null != currency) || subtrahend.currency == currency);
		return new Money(currency, setScaleRoundingMode(amount.subtract(subtrahend.amount)));
	}

	public Money multiply(double multiplier) {
		return new Money(currency, setScaleRoundingMode(amount.multiply(createAmount(multiplier))));
	}

	public Money add(Money augend) {
		assert ((EMPTY_MONEY == this && null != augend.currency) || augend.currency == currency);
		return new Money(augend.currency, setScaleRoundingMode(amount.add(augend.amount)));
	}

	@Override
	public String toString() {
		return currency == null ? "NULL" : String.format("%s %s", currency.getSymbol(), amount);
	}

	private static BigDecimal setScaleRoundingMode(BigDecimal bigDecimal) {
		return bigDecimal.setScale(DEFAULT_MONEY_SCALE, DEFAULT_MONEY_ROUNDINGMODE);
	}
}
