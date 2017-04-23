package com.foo.shoppingcart;

import static org.junit.Assert.assertEquals;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

public class BulkDiscountTest {
	private static final Currency AUD = Currency.getInstance("AUD");

	private Product product;
	private CompositePricingRule pricingRule;
	private PricingRule bulkDiscount;

	@Before
	public void setUp() throws Exception {
		this.product = new Product("ult_large", "Unlimited 5GB", new Money(AUD, 44.9));
		this.pricingRule = new CompositePricingRule();
		this.bulkDiscount = new BulkDiscount(product, 3, new Money(AUD, 39.9));
		pricingRule.add(bulkDiscount);
	}

	@Test
	public void testApplyBulkDiscount() {
		ShoppingCart cart = ShoppingCart.create(pricingRule);
		cart.add(product);
		assertEquals("AUD 44.90", cart.total().toString());
		cart.add(product);
		assertEquals("AUD 89.80", cart.total().toString());
		cart.add(product);
		assertEquals("AUD 134.70", cart.total().toString());
		cart.add(product);
		assertEquals("AUD 159.60", cart.total().toString());
	}

	@Test
	public void testToString() {
		assertEquals("Drop to AUD 39.90 on more than 3 on \"Unlimited 5GB\"", bulkDiscount.toString());
	}
}
