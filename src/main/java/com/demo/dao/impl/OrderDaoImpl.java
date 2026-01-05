package com.demo.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.demo.dao.OrderDao;
import com.demo.entity.Order;
import com.demo.web.dto.order.OrderSummaryDTO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class OrderDaoImpl implements OrderDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public Order save(Order order) {
		em.persist(order);
		return order;
	}

	@Override
	public Optional<Order> findByIdAndUserId(Integer userId, Integer id) {
		var list = em.createQuery("""
				select distinct o
				from Order o
				left join fetch o.items i
				left join fetch i.product p
				where o.id = :oid and o.user.id = :uid
				""", Order.class).setParameter("oid", id).setParameter("uid", userId).getResultList();

		return list.stream().findFirst();
	}
	

	@Override
	public List<OrderSummaryDTO> findSummariesByUserId(Integer userId) {
		return em.createQuery("""
		        select new com.demo.web.dto.order.OrderSummaryDTO(
		            o.id,
		            o.status,
		            o.total,
		            o.createdAt
		        )
		        from Order o
		        where o.user.id = :uid
		        order by o.createdAt desc
		    """, OrderSummaryDTO.class)
		    .setParameter("uid", userId)
		    .getResultList();
	}



}
