package com.foo.shoppingcart;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompositePricingRule implements PricingRule {
	private Logger log = LoggerFactory.getLogger(CompositePricingRule.class);

	private List<PricingRule> pricingRules = new ArrayList<PricingRule>();

	public void add(PricingRule pricingRule) {
		pricingRules.add(pricingRule);
	}

	@Override
	public void apply(Item item) {
		for (PricingRule pricingRule : pricingRules) {
			log.info("Applying pricing rule {}", pricingRule);
			if (item instanceof ShoppingCart) {
				ShoppingCart cart = (ShoppingCart) item;
				Collection<ShoppingCartItem> items = cart.items();
				for (ShoppingCartItem shoppingCartItem : items) {
					pricingRule.apply(shoppingCartItem);
				}
			}
			pricingRule.apply(item);
		}
	}
}
