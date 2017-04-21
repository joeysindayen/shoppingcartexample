package com.foo.shoppingcart;

import java.util.Currency;

public class Product {
	private String code;
	private String name;
	private Money price;

	public Product(String code, String name, Money price) {
		super();
		this.code = code;
		this.name = name;
		this.price = price;
	}

	public Product(String code, String name, Currency currency, double price) {
		this(code, name, new Money(currency, price));
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public Money getPrice() {
		return price;
	}
}
