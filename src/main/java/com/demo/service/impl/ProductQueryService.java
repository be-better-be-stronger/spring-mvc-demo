package com.demo.service.impl;

import com.demo.dao.ProductDao;
import com.demo.exception.BadRequestException;
import com.demo.web.dto.ProductListRow;
import com.demo.web.filter.ProductFilter;
import com.demo.web.paging.PageRequest;
import com.demo.web.paging.PageResponse;
import org.springframework.stereotype.Service;

@Service
public class ProductQueryService {

    private final ProductDao productDao;

    public ProductQueryService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public PageResponse<ProductListRow> search(ProductFilter filter, PageRequest pr) {
        // validate categoryId kiểu production
        if (filter.getCategoryId() != null && filter.getCategoryId() <= 0) {
            throw new BadRequestException("categoryId must be > 0");
        }

        String kw = (filter.getKeyword() == null) ? null : filter.getKeyword().trim();
        if (kw != null && kw.isEmpty()) kw = null;
        filter.setKeyword(kw);
        
        // query lần 1
        PageResponse<ProductListRow> res = productDao.search(filter, pr);
        long totalPages = res.getTotalPages();
        // clamp page nếu user truyền page quá lớn (trải nghiệm admin tốt hơn)
        if (res.getTotalPages() > 0 && pr.page() > totalPages) {
            PageRequest pr2 = pr.withPage((int) totalPages);
            return productDao.search(filter, pr2);
        }
        return res;
    }
}
