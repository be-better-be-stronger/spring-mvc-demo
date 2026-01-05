package com.demo.dao.impl;

import com.demo.dao.ProductDao;
import com.demo.entity.Product;
import com.demo.web.dto.ProductListRow;
import com.demo.web.filter.ProductFilter;
import com.demo.web.paging.PageRequest;
import com.demo.web.paging.PageResponse;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductDaoImpl implements ProductDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void persist(Product p) {
		em.persist(p);
	}

	@Override
	public long countActive() {
		return em.createQuery("""
				    select count(p)
				    from Product p
				    where p.active = true
				""", Long.class).getSingleResult();
	}

	@Override
	public List<Product> findActivePageWithCategory(int offset, int limit) {
		return em.createQuery("""
				    select p
				    from Product p
				    join fetch p.category c
				    where p.active = true
				    order by p.id desc
				""", Product.class).setFirstResult(offset).setMaxResults(limit).getResultList();
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return Optional.ofNullable(em.find(Product.class, id));
	}

	@Override
	public PageResponse<ProductListRow> search(ProductFilter f, PageRequest pr) {
		// Build WHERE
		String where = """
		        where (:active is null or p.active = :active)
		          and (:cid is null or c.id = :cid)
		          and (:kw is null or lower(p.name) like :kw)
		    """;

		    String kwParam = (f.getKeyword() == null) ? null : "%" + f.getKeyword().toLowerCase() + "%";

		// COUNT
		Long total = em.createQuery("""
				    select count(p.id)
				    from Product p
				    join p.category c
				""" + where, Long.class)
				.setParameter("active", f.getActive())
				.setParameter("cid", f.getCategoryId())
				.setParameter("kw", kwParam)
				.getSingleResult();

		// ORDER BY (whitelist đã validate ở controller/service rồi)
		String orderBy = " order by p." + pr.sort() + " " + pr.dir();

		// DATA query -> DTO projection (tránh fetch join + pagination drama)
		var items = em.createQuery("""
				    select new com.demo.web.dto.ProductListRow(
				        p.id, p.name, p.stock, p.price, c.id, c.name
				    )
				    from Product p
				    join p.category c
				""" + where + orderBy, ProductListRow.class)
				.setParameter("active", f.getActive())
				.setParameter("cid", f.getCategoryId())
				.setParameter("kw", kwParam)
				.setFirstResult(pr.offset())
				.setMaxResults(pr.size())
				.getResultList();

		// Nếu page vượt totalPages thì có thể “clamp” ở service/controller
		return new PageResponse<>(items, pr.page(), pr.size(), total);
	}

	@Override
	public ProductListRow findPublicDetailById(int id) {
	    var list = em.createQuery("""
	        select new com.demo.web.dto.ProductListRow(
	            p.id, p.name, p.stock, p.price,
	            c.id, c.name
	        )
	        from Product p
	        join p.category c
	        where p.id = :id
	          and p.active = true
	    """, ProductListRow.class)
	    .setParameter("id", id)
	    .setMaxResults(1)
	    .getResultList();

	    return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Optional<Product> findByIdForUpdate(Integer id) {
		Product p = em.find(Product.class, id, LockModeType.PESSIMISTIC_WRITE);
	    return Optional.ofNullable(p);
	}	

}
