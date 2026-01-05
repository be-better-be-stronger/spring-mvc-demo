package com.demo.web.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.demo.entity.OrderStatus;

public class OrderSummaryDTO {
	private Integer id;
	private OrderStatus status;
	private BigDecimal total;
	private LocalDateTime createdAt;

	public OrderSummaryDTO(Integer id, OrderStatus status, BigDecimal total, LocalDateTime createdAt) {
		this.id = id;
		this.status = status;
		this.total = total;
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
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

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	

}
