package com.foo.shoppingcart;

public class ShoppingCartItem implements Item {
	private Product product;
	private int quantity;
	private Money discount = Money.EMPTY_MONEY;

	public ShoppingCartItem(Product product) {
		this(product, 1);
	}

	public ShoppingCartItem(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public void increment() {
		quantity++;
	}

	public Product getProduct() {
		return product;
	}

	public String getProductCode() {
		return product.getCode();
	}

	public Money getProductPrice() {
		return product.getPrice();
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public void addDiscount(Money discount) {
		this.discount = this.discount.add(discount);
	}

	@Override
	public Money total() {
		return product.getPrice().multiply(quantity).subtract(discount);
	}

	@Override
	public String toString() {
		return String.format("%d x %s", quantity, product.getName());
	}
}
