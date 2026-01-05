package com.demo.dao;

import com.demo.entity.Product;
import com.demo.web.dto.ProductListRow;
import com.demo.web.filter.ProductFilter;
import com.demo.web.paging.PageRequest;
import com.demo.web.paging.PageResponse;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
	long countActive();
    List<Product> findActivePageWithCategory(int offset, int limit);
    void persist(Product p);
    Optional<Product> findById(Integer id);    
    Optional<Product> findByIdForUpdate(Integer id);    
    
    PageResponse<ProductListRow> search(ProductFilter filter, PageRequest pr);
    ProductListRow findPublicDetailById(int id);
}
