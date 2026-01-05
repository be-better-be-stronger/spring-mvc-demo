package com.demo.dao;

import java.util.List;

import com.demo.entity.Category;

public interface CategoryDao {
    List<Category> findAll();
    Category findById(Integer id);
    Category findByName(String name);
    void persist(Category c);
    void remove(Category c);
}
