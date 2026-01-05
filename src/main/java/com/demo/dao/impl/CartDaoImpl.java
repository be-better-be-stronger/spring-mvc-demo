package com.demo.dao.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.demo.dao.CartDao;
import com.demo.entity.Cart;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CartDaoImpl implements CartDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Optional<Cart> findByUserId(Integer userId) {
        return em.createQuery("""
                select c from Cart c
                where c.user.id = :uid
                """, Cart.class)
                .setParameter("uid", userId)
                .getResultStream().findFirst();
    }

    @Override
    public Optional<Cart> findByUserIdWithItems(Integer userId) {
        return em.createQuery("""
                select distinct c from Cart c
                left join fetch c.items i
                left join fetch i.product p
                where c.user.id = :uid
                """, Cart.class)
                .setParameter("uid", userId)
                .getResultStream().findFirst();
    }

    @Override
    public Cart save(Cart cart) {
        if (cart.getId() == null) {
            em.persist(cart);
            return cart;
        }
        return em.merge(cart);
    }
}
