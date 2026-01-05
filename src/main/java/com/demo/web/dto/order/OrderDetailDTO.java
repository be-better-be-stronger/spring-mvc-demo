package com.demo.web.dto.order;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.demo.entity.Order;

public class OrderDetailDTO {
	private Integer id;
    private String status;
    private BigDecimal total;
    private LocalDateTime createdAt;
    private LocalDateTime cancelledAt;
    private List<OrderItemDTO> items;
    
	public static OrderDetailDTO toDTO(Order o) {
		OrderDetailDTO dto = new OrderDetailDTO();
		dto.setId(o.getId());
		dto.setStatus(o.getStatus() == null ? null : o.getStatus().name());

		dto.setTotal(o.getTotal());
		dto.setCreatedAt(o.getCreatedAt());
		dto.setCancelledAt(o.getCancelledAt());
		dto.setItems(o.getItems().stream()
				.map(i -> OrderItemDTO.toDTO(i))
				.toList()
		);
		return dto;
	}
    
	
	public Integer getId() {
		return id;
	}
	public LocalDateTime getCancelledAt() {
		return cancelledAt;
	}

	public void setCancelledAt(LocalDateTime cancelledAt) {
		this.cancelledAt = cancelledAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
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
	public List<OrderItemDTO> getItems() {
		return items;
	}
	public void setItems(List<OrderItemDTO> items) {
		this.items = items;
	}


}