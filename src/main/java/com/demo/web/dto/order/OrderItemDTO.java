package com.demo.web.dto.order;

import java.math.BigDecimal;

import com.demo.entity.OrderItem;

public class OrderItemDTO {
    private String productName;
    private BigDecimal unitPrice;
    private int qty;
    private BigDecimal lineTotal;
 
    
    public static OrderItemDTO toDTO(OrderItem oi) {
    	OrderItemDTO dto = new OrderItemDTO();
    	dto.setProductName(oi.getProduct().getName());
    	dto.setUnitPrice(oi.getUnitPrice());
    	dto.setQty(oi.getQty());
    	dto.setLineTotal(oi.getLineTotal());
		return dto;    	
    }
    

	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	public BigDecimal getLineTotal() {
		return lineTotal;
	}
	public void setLineTotal(BigDecimal lineTotal) {
		this.lineTotal = lineTotal;
	}

	
    
    
}