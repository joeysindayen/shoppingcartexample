package com.foo.shoppingcart;

import static org.junit.Assert.assertEquals;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

public class DealDiscountTest {
	private static final Currency AUD = Currency.getInstance("AUD");

	private Product product;
	private CompositePricingRule pricingRule;
	private PricingRule dealDiscount;

	@Before
	public void setUp() throws Exception {
		this.product = new Product("ult_small", "Unlimited 1GB", new Money(AUD, 24.9));
		this.pricingRule = new CompositePricingRule();
		this.dealDiscount = new DealDiscount(this.product, 3, 2);
		pricingRule.add(this.dealDiscount);
	}

	@Test
	public void testApplyDealDiscount() {
		ShoppingCart cart = ShoppingCart.create(pricingRule);
		cart.add(product);
		assertEquals("AUD 24.90", cart.total().toString());
		cart.add(product);
		assertEquals("AUD 49.80", cart.total().toString());
		cart.add(product);
		assertEquals("AUD 49.80", cart.total().toString());
		cart.add(product);
		assertEquals("AUD 74.70", cart.total().toString());
		cart.add(product);
		assertEquals("AUD 99.60", cart.total().toString());
		cart.add(product);
		assertEquals("AUD 99.60", cart.total().toString());
	}

	@Test
	public void testToString() {
		assertEquals("3 of 2 deal on \"Unlimited 1GB\"", dealDiscount.toString());
	}
}
