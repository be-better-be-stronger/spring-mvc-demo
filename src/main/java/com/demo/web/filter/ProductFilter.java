package com.demo.web.filter;

public class ProductFilter {
    private Integer categoryId; // null = all
    private String keyword; // search by name (LIKE)
    private Boolean active = true;
    
    public ProductFilter(Integer categoryId, String keyword) {
		this.categoryId = categoryId;
		this.keyword = keyword;
	}   

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }
    
    public String getKeyword() { return keyword; }
	public void setKeyword(String keyword) { this.keyword = keyword; }

    public Boolean getActive() { return active; } 
	public void setActive(Boolean active) { this.active = active; }
	
}
