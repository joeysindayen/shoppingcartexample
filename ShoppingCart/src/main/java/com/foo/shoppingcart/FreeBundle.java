package com.foo.shoppingcart;

public class FreeBundle implements PricingRule {
	private Product product;
	private Product bundle;

	public FreeBundle(Product product, Product bundle) {
		super();
		this.product = product;
		this.bundle = bundle;
	}

	@Override
	public void apply(Item item) {
		if (item instanceof ShoppingCart) {
			ShoppingCart cart = (ShoppingCart) item;
			ShoppingCartItem cartItem = cart.find(product);
			int quantity = cartItem == null ? 0 : cartItem.getQuantity();
			while (quantity-- > 0) {
				cart.insertFreeBundle(bundle);
			}
		}
	}

	@Override
	public String toString() {
		return String.format("Free \"%s\" bundled for every \"%s\"", bundle.getName(), product.getName());
	}
}
