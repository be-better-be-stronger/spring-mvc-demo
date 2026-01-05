package com.demo.dao;

import java.util.Optional;

import com.demo.entity.Cart;

public interface CartDao {
    Optional<Cart> findByUserId(Integer userId);
    Optional<Cart> findByUserIdWithItems(Integer userId); // join fetch để dùng trong view
    Cart save(Cart cart);
}
