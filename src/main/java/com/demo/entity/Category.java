package com.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

 // mappedBy = tên field ở Product (owning side nằm ở Product)
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
    
    public Category() {}
    
    // helper để giữ đồng bộ 2 chiều (cực quan trọng)
    public void addProduct(Product p) {
        products.add(p);
        p.setCategory(this);
    }
    public void removeProduct(Product p) {
        products.remove(p);
        p.setCategory(null);
    }

    public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
