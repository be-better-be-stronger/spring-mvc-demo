package com.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 30)	
	private OrderStatus status = OrderStatus.PLACED;

	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal total = BigDecimal.ZERO;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<OrderItem> items = new ArrayList<>();
	
	@Column(name = "cancelled_at")
	private LocalDateTime cancelledAt;


	@PrePersist
	protected void prePersist() {
		if (createdAt == null)
			createdAt = LocalDateTime.now();
		if (status == null)
			status = OrderStatus.PLACED;
		if (total == null)
			total = BigDecimal.ZERO;
	}

	public void addItem(OrderItem item) {
		items.add(item);
		item.setOrder(this);
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

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		if (status == null) {
	        throw new IllegalArgumentException("OrderStatus must not be null");
	    }
		this.status = status;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public List<OrderItem> getItems() {
		return items;
	}

	public void setItems(List<OrderItem> items) {
		this.items = items;
	}
	
	public LocalDateTime getCancelledAt() {
	    return cancelledAt;
	}

	public void setCancelledAt(LocalDateTime cancelledAt) {
	    this.cancelledAt = cancelledAt;
	}

}
