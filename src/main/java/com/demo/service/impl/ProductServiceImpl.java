package com.demo.service.impl;

import com.demo.dao.ProductDao;
import com.demo.entity.Category;
import com.demo.entity.Product;
import com.demo.exception.BadRequestException;
import com.demo.exception.NotFoundException;
import com.demo.service.ProductService;
import com.demo.util.Require;
import com.demo.util.TextUtil;
import com.demo.web.dto.ProductListRow;
import com.demo.web.dto.ProductForm;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {

	private final ProductDao productDao;

	@PersistenceContext
	private EntityManager em;

	public ProductServiceImpl(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Override
	public Product getRequired(Integer id) {
		Integer pid = Require.positive(id, "id");
		Product p = productDao.findById(pid).orElseThrow(() -> new NotFoundException("Product not found"));
		return p;
	}

	@Transactional
	@Override
	public void create(ProductForm form) {
		Require.notNull(form, "form");
		validateValue(form);

		Category c = getCategoryRequired(form.getCategoryId());

		Product p = new Product();
		applyFormToEntity(form, p, c);

		p.setActive(true); // create: active mặc định true
		productDao.persist(p);
	}

	@Transactional
	@Override
	public void update(Integer id, ProductForm form) {
		Require.notNull(form, "form");
		validateValue(form);

		Product p = getRequired(id); // managed
		Category c = getCategoryRequired(form.getCategoryId());

		applyFormToEntity(form, p, c); // dirty checking -> commit tự update
	}

	@Transactional
	@Override
	public void softDelete(Integer id) {
		Product p = getRequired(id);
		p.setActive(false);
	}
	


	// ===== helpers =====

	private void validateValue(ProductForm form) {
		// service validate VALUE (null/range), không validate TYPE
		String name = Require.notBlank(form.getName(), "name");
		Require.nonNegative(form.getStock(), "stock");
		BigDecimal price = Require.notNull(form.getPrice(), "price");
		if (price.signum() < 0)
			throw new BadRequestException("price must be >= 0");
		Require.positive(form.getCategoryId(), "categoryId");

		// normalize lại để tránh name = " "
		String n = TextUtil.normalizeName(name);
		if (n.isBlank())
			throw new BadRequestException("name is required");
	}

	private Category getCategoryRequired(Integer categoryId) {
		Integer cid = Require.positive(categoryId, "categoryId");
		Category c = em.find(Category.class, cid);
		if (c == null)
			throw new NotFoundException("Category not found");
		return c;
	}

	private void applyFormToEntity(ProductForm form, Product p, Category c) {
		p.setName(TextUtil.normalizeName(form.getName()));
		p.setStock(form.getStock());
		p.setPrice(form.getPrice());
		p.setCategory(c);
	}

	@Override
	public ProductListRow findPublicDetailById(Integer id) {
		Integer pId = Require.positive(id, "id");
		ProductListRow p = productDao.findPublicDetailById(pId);
		if(p == null) throw new NotFoundException("Product not found");
		return p;
	}

}
