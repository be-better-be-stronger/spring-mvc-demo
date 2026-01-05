package com.demo.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.CategoryDao;
import com.demo.entity.Category;
import com.demo.exception.BadRequestException;
import com.demo.exception.ConflictException;
import com.demo.exception.NotFoundException;
import com.demo.service.CategoryService;
import com.demo.util.TextUtil;

@Service
public class CategoryServiceImpl implements CategoryService {

	private final CategoryDao categoryDao;

	public CategoryServiceImpl(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Override
	public List<Category> list() {
		return categoryDao.findAll();
	}

	@Override
	public Category getRequired(Integer id) {
		if (id == null || id <= 0)
			throw new BadRequestException("id invalid");
		Category c = categoryDao.findById(id);
		if (c == null)
			throw new NotFoundException("Category not found");
		return c;
	}

	@Override
	@Transactional
	public void create(String name) {
		String n = TextUtil.normalizeName(name);
		if (categoryDao.findByName(n) != null) {
			throw new ConflictException("Category name already exists");
		}
		Category c = new Category();
		c.setName(n);
		categoryDao.persist(c);
	}

	@Override
	@Transactional
	public void update(Integer id, String name) {
		String n = TextUtil.normalizeName(name);

		Category c = getRequired(id);

		Category sameName = categoryDao.findByName(n);
		if (sameName != null && !sameName.getId().equals(id)) {
			throw new BadRequestException("Category name already exists");
		}

		c.setName(n);
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		Category c = getRequired(id);
		if (c.getProducts() != null && !c.getProducts().isEmpty()) 
			throw new ConflictException("Cannot delete: category has products");
			
		categoryDao.remove(c);
	}

}
