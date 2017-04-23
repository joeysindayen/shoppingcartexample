package com.foo.shoppingcart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

public class FreeBundleTest {
	private static final Currency AUD = Currency.getInstance("AUD");

	private Product product;
	private Product bundle;
	private CompositePricingRule pricingRule;
	private PricingRule freeBundle;

	@Before
	public void setUp() throws Exception {
		this.product = new Product("ult_medium", "Unlimited 2GB", new Money(AUD, 29.9));
		this.bundle = new Product("1gb", "1 GB Data-pack", new Money(AUD, 9.9));
		this.pricingRule = new CompositePricingRule();
		this.freeBundle = new FreeBundle(product, bundle);
		pricingRule.add(this.freeBundle);
	}

	@Test
	public void testApplyFreeBundle() {
		ShoppingCart cart = ShoppingCart.create(pricingRule);
		cart.add(product);

		Collection<ShoppingCartItem> items = cart.items();

		assertEquals(2, items.size());

		String itemsStr = items.toString();
		assertTrue(itemsStr.contains("1 x Unlimited 2GB"));
		assertTrue(itemsStr.contains("1 x 1 GB Data-pack"));

		assertEquals("AUD 29.90", cart.total().toString());
	}

	@Test
	public void testToString() {
		assertEquals("Free \"1 GB Data-pack\" bundled for every \"Unlimited 2GB\"", freeBundle.toString());
	}
}
