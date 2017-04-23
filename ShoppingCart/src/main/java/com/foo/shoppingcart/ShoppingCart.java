package com.foo.shoppingcart;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart implements Item {
	private Map<String, ShoppingCartItem> items = new LinkedHashMap<String, ShoppingCartItem>();
	private Map<String, ShoppingCartItem> processedItems = new LinkedHashMap<String, ShoppingCartItem>();
	private PricingRule pricingRule;
	private String promoCode = "";
	private Money discount = Money.EMPTY_MONEY;

	private ShoppingCart(PricingRule pricingRule) {
		this.pricingRule = pricingRule;
	}

	public static ShoppingCart create(PricingRule pricingRule) {
		return new ShoppingCart(pricingRule);
	}

	public void add(Product product) {
		insert(items, product);
		processedItems.clear();
	}

	public void add(Product product, String promoCode) {
		add(product);
		this.promoCode = promoCode;
	}

	@Override
	public Money total() {
		applyPricingRule();
		return itemsTotal().subtract(discount);
	}

	public Collection<ShoppingCartItem> items() {
		applyPricingRule();
		return Collections.unmodifiableCollection(processedItems.values());
	}

	@Override
	public void addDiscount(Money discount) {
		this.discount = this.discount.add(discount);
	}

	public String getPromoCode() {
		return promoCode;
	}

	ShoppingCartItem find(Product product) {
		return items.get(product.getCode());
	}

	Money itemsTotal() {
		Collection<ShoppingCartItem> values = processedItems.values();
		Money total = Money.EMPTY_MONEY;
		for (ShoppingCartItem item : values) {
			total = total.add(item.total());
		}
		return total;
	}

	void insertFreeBundle(Product bundle) {
		if (processedItems.containsKey(bundle.getCode())) {
			ShoppingCartItem item = processedItems.get(bundle.getCode());
			item.increment();
			item.addDiscount(bundle.getPrice());
		} else {
			ShoppingCartItem item = new ShoppingCartItem(bundle);
			item.addDiscount(bundle.getPrice());
			processedItems.put(bundle.getCode(), item);
		}
	}

	private void insert(Map<String, ShoppingCartItem> items, Product product) {
		if (items.containsKey(product.getCode())) {
			ShoppingCartItem item = items.get(product.getCode());
			item.increment();
		} else {
			items.put(product.getCode(), new ShoppingCartItem(product));
		}
	}

	private void applyPricingRule() {
		if (processedItems.isEmpty()) {
			Collection<ShoppingCartItem> values = items.values();
			for (ShoppingCartItem item : values) {
				for (int i = item.getQuantity(); i > 0; i--) {
					insert(processedItems, item.getProduct());
				}
			}
			pricingRule.apply(this);
		}
	}
}
