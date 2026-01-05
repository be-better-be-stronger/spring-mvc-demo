package com.demo.service;

import com.demo.entity.Product;
import com.demo.web.dto.ProductListRow;


public interface ProductService {
	
    Product getRequired(Integer id);

    void create(com.demo.web.dto.ProductForm form);
    void update(Integer id, com.demo.web.dto.ProductForm form);

    void softDelete(Integer id);
    
    ProductListRow findPublicDetailById(Integer id);

}
