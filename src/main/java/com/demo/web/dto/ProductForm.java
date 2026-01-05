package com.demo.web.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public class ProductForm {

    private Integer id;          // null = create, != null = edit
    
    @NotBlank(message = "Name is required")
    @Size(max = 100)
    private String name;
    
    @NotNull(message = "Stock is required")
    @Min(value = 0, message = "Stock must be >= 0")
    private Integer stock;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be > 0")
    private BigDecimal price;
    
    @NotNull(message = "Category is required")
    @Positive(message = "Category must be > 0")
    private Integer categoryId;

    // getter / setter
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
}
