package com.foo.shoppingcart;

public class PromoCodeDiscount implements PricingRule {
	private String promoCode;
	private double percentDiscount;

	public PromoCodeDiscount(String promoCode, double percentDiscount) {
		super();
		this.promoCode = promoCode;
		this.percentDiscount = percentDiscount;
	}

	@Override
	public void apply(Item item) {
		if (item instanceof ShoppingCart) {
			ShoppingCart cart = (ShoppingCart) item;
			if (cart.getPromoCode().matches(promoCode)) {
				cart.addDiscount(cart.itemsTotal().multiply(percentDiscount));
			}
		}
	}

	@Override
	public String toString() {
		return String.format("%.0f%% discount across the board for promo code \"%s\"", 100 * percentDiscount,
				promoCode);
	}
}
