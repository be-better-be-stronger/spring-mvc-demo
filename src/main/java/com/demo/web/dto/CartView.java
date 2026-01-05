package com.demo.web.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CartView {
    public static class Row {
        public Integer productId;
        public String productName;
        public BigDecimal unitPrice;
        public int qty;

        public Row() {}

        public Row(Integer productId, String productName, BigDecimal unitPrice, int qty) {
            this.productId = productId;
            this.productName = productName;
            this.unitPrice = unitPrice;
            this.qty = qty;
        }

        public Integer getProductId() { return productId; }
        public void setProductId(Integer productId) { this.productId = productId; }

        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }

        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

        public int getQty() { return qty; }
        public void setQty(int qty) { this.qty = qty; }

        public BigDecimal getLineTotal() { // ✅ JSP gọi ${it.lineTotal}
            return unitPrice.multiply(BigDecimal.valueOf(qty));
        }
    }

    private final List<Row> items = new ArrayList<>();

    public List<Row> getItems() { return items; }

    public BigDecimal getGrandTotal() {
        return items.stream()
                .map(Row::getLineTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalQty() {
        return items.stream().mapToInt(r -> r.qty).sum();
    }
}
 