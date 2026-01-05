package com.demo.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items", uniqueConstraints = @UniqueConstraint(columnNames = { "cart_id", "product_id" }))
public class CartItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cart_id")
	private Cart cart;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "product_id")
	private Product product;

	@Column(nullable = false)
	private int qty;

	@Column(name = "unit_price", nullable = false, precision = 12, scale = 2)
	private BigDecimal unitPrice;

	protected CartItem() {
	}

	public CartItem(Cart cart, Product product, int qty, BigDecimal unitPrice) {
		this.cart = cart; // owning side: FK nằm ở đây
		this.product = product;
		this.qty = qty;
		this.unitPrice = unitPrice;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}
