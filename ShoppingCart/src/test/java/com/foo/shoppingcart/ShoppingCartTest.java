package com.foo.shoppingcart;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShoppingCartTest {
	private Logger log = LoggerFactory.getLogger(ShoppingCartTest.class);

	private static final Currency AUD = Currency.getInstance("AUD");

	private static final Product NULL_PRODUCT = new Product("", "", Money.EMPTY_MONEY);

	private Map<String, Product> products = new HashMap<String, Product>();
	private CompositePricingRule pricingRules = new CompositePricingRule();

	@Before
	public void setUp() throws Exception {
		addProduct(new Product("ult_small", "Unlimited 1GB", new Money(AUD, 24.9)));
		addProduct(new Product("ult_medium", "Unlimited 2GB", new Money(AUD, 29.9)));
		addProduct(new Product("ult_large", "Unlimited 5GB", new Money(AUD, 44.9)));
		addProduct(new Product("1gb", "1 GB Data-pack", new Money(AUD, 9.9)));

		pricingRules.add(new DealDiscount(findProduct("ult_small"), 3, 2));
		pricingRules.add(new BulkDiscount(findProduct("ult_large"), 3, new Money(AUD, 39.9)));
		pricingRules.add(new FreeBundle(findProduct("ult_medium"), findProduct("1gb")));
		pricingRules.add(new PromoCodeDiscount("I<3AMAYSIM", .1));
	}

	@Test
	public void testScenario1() {
		log.info("----------------------------------------");
		log.info("Scenario 1");
		log.info("----------------------------------------");
		ShoppingCart cart = ShoppingCart.create(pricingRules);
		Product product = findProduct("ult_small");
		cart.add(product);
		cart.add(product);
		cart.add(product);
		cart.add(findProduct("ult_large"));

		Collection<ShoppingCartItem> items = cart.items();
		assertEquals(2, items.size());
		for (Item item : items) {
			log.info("{}", item);
		}
		Money total = cart.total();
		log.info("{}", total);
		assertEquals("AUD 94.70", total.toString());
	}

	@Test
	public void testScenario2() {
		log.info("----------------------------------------");
		log.info("Scenario 2");
		log.info("----------------------------------------");
		ShoppingCart cart = ShoppingCart.create(pricingRules);

		Product product = findProduct("ult_small");
		cart.add(product);
		cart.add(product);

		product = findProduct("ult_large");
		cart.add(product);
		cart.add(product);
		cart.add(product);
		cart.add(product);

		Collection<ShoppingCartItem> items = cart.items();
		assertEquals(2, items.size());
		for (Item item : items) {
			log.info("{}", item);
		}
		Money total = cart.total();
		log.info("{}", total);
		assertEquals("AUD 209.40", total.toString());
	}

	@Test
	public void testScenario3() {
		log.info("----------------------------------------");
		log.info("Scenario 3");
		log.info("----------------------------------------");
		ShoppingCart cart = ShoppingCart.create(pricingRules);

		cart.add(findProduct("ult_small"));

		Product product = findProduct("ult_medium");
		cart.add(product);
		cart.add(product);

		Collection<ShoppingCartItem> items = cart.items();
		assertEquals(3, items.size());
		for (Item item : items) {
			log.info("{}", item);
		}
		Money total = cart.total();
		log.info("{}", total);
		assertEquals("AUD 84.70", total.toString());
	}

	@Test
	public void testScenario4() {
		log.info("----------------------------------------");
		log.info("Scenario 4");
		log.info("----------------------------------------");
		ShoppingCart cart = ShoppingCart.create(pricingRules);
		cart.add(findProduct("ult_small"));
		cart.add(findProduct("1gb"), "I<3AMAYSIM");

		Collection<ShoppingCartItem> items = cart.items();
		assertEquals(2, items.size());
		for (Item item : items) {
			log.info("{}", item);
		}
		Money total = cart.total();
		log.info("{}", total);
		assertEquals("AUD 31.32", total.toString());
	}

	private void addProduct(Product product) {
		products.put(product.getCode(), product);
	}

	private Product findProduct(String code) {
		return products.getOrDefault(code, NULL_PRODUCT);
	}
}
