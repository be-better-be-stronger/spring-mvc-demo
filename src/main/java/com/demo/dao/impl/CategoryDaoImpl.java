package com.demo.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.demo.dao.CategoryDao;
import com.demo.entity.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CategoryDaoImpl implements CategoryDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<Category> findAll() {
		return em.createQuery("select c from Category c order by c.name", Category.class).getResultList();
	}

	@Override
	public Category findById(Integer id) {
		return em.find(Category.class, id);
	}

	@Override
	public Category findByName(String name) {
		var list = em.createQuery("select c from Category c where c.name = :name", Category.class)
				.setParameter("name", name).setMaxResults(1).getResultList();
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
    public void persist(Category c) {
        em.persist(c);
    }

    @Override
    public void remove(Category c) {
        em.remove(c); // c phải managed
    }
	

}
