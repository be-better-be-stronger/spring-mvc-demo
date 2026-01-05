package com.demo.web.dto;

import java.math.BigDecimal;

public class ProductListRow {
    private final Integer id;
    private final String name;
    private final Integer stock;
    private final BigDecimal price;
    private final Integer categoryId;
    private final String categoryName;

    public ProductListRow(Integer id, String name, Integer stock, BigDecimal price,
                          Integer categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public Integer getStock() { return stock; }
    public BigDecimal getPrice() { return price; }
    public Integer getCategoryId() { return categoryId; }
    public String getCategoryName() { return categoryName; }
}
