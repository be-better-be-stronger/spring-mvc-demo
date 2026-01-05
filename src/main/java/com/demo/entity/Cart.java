package com.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "carts")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", unique = true, nullable = false)
	private User user;

	@OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CartItem> items = new ArrayList<>();

	public void addOrInc(Product p, int qty) {		
		CartItem it = items.stream().filter(x -> x.getProduct().getId().equals(p.getId())).findFirst().orElse(null);
		if (it == null) {
			it = new CartItem(this, p, qty, p.getPrice());
			items.add(it); // inverse list
		} else {
			if(qty <= 0) it.setQty(0);
			else it.setQty(it.getQty() + qty); // dirty checking
		}
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<CartItem> getItems() {
		return items;
	}

	public void setItems(List<CartItem> items) {
		this.items = items;
	}

	public void removeProduct(Integer productId) {
		items.removeIf(x -> x.getProduct().getId().equals(productId));
		// orphanRemoval => DELETE cart_items
	}

	public int getQtyOf(int productId) {
		 return items.stream()
			        .filter(i -> i.getProduct().getId() == productId)
			        .mapToInt(CartItem::getQty)
			        .findFirst()
			        .orElse(0);
	}
	
	public CartItem findItem(Integer productId) {
		for(CartItem it : items) {
			if(it.getProduct().getId().equals(productId)) return it;
		}
		return null;
	}
}
