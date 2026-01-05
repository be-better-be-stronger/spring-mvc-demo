package com.demo.service;

import java.util.List;
import com.demo.entity.Category;

public interface CategoryService {
    List<Category> list();
    Category getRequired(Integer id);     // not found -> throw
    void create(String name);
    void update(Integer id, String name);
    void delete(Integer id);
}
