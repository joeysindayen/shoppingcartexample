package com.foo.shoppingcart;

public interface Item {
	public void addDiscount(Money discount);

	public Money total();
}
