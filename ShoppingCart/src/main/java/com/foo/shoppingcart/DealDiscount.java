package com.foo.shoppingcart;

public class DealDiscount implements PricingRule {
	private Product product;
	private int quantity;
	private int discountQuantity;

	public DealDiscount(Product product, int quantity, int discountQuantity) {
		super();
		this.product = product;
		this.quantity = quantity;
		this.discountQuantity = discountQuantity;
	}

	@Override
	public void apply(Item item) {
		if (item instanceof ShoppingCartItem) {
			ShoppingCartItem cartItem = (ShoppingCartItem) item;
			int itemQuantity = cartItem.getQuantity();
			if (cartItem.getProductCode().matches(product.getCode()) && quantity <= itemQuantity) {
				int difference = quantity - discountQuantity;
				cartItem.addDiscount(product.getPrice().multiply((itemQuantity / quantity) * difference));
			}
		}
	}

	@Override
	public String toString() {
		return String.format("%d of %d deal on \"%s\"", quantity, discountQuantity, product.getName());
	}
}
