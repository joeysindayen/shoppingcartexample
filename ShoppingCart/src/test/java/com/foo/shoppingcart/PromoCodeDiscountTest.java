package com.foo.shoppingcart;

import static org.junit.Assert.assertEquals;
import java.util.Currency;

import org.junit.Before;
import org.junit.Test;

public class PromoCodeDiscountTest {
	private static final Currency AUD = Currency.getInstance("AUD");

	private Product product;
	private CompositePricingRule pricingRule;
	private PricingRule promoCodeDiscount;

	@Before
	public void setUp() throws Exception {
		this.product = new Product("ult_small", "Unlimited 1GB", new Money(AUD, 24.9));
		this.pricingRule = new CompositePricingRule();
		this.promoCodeDiscount = new PromoCodeDiscount("I<3AMAYSIM", .1);
		pricingRule.add(this.promoCodeDiscount);
	}

	@Test
	public void testApplyPromoCodeDiscount() {
		ShoppingCart cart = ShoppingCart.create(pricingRule);
		cart.add(product);
		assertEquals("AUD 24.90", cart.total().toString());
		cart.add(product, "AMAYSIMPROMO");
		assertEquals("AUD 49.80", cart.total().toString());
		cart.add(product, "I<3AMAYSIM");
		assertEquals("AUD 67.23", cart.total().toString());
	}

	@Test
	public void testToString() {
		assertEquals("10% discount across the board for promo code \"I<3AMAYSIM\"", promoCodeDiscount.toString());
	}
}
