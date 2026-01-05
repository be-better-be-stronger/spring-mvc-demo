package com.demo.service;

import com.demo.web.dto.CartView;

public interface CartService {
    CartView getView(Integer userId);

    void add(Integer userId, Integer productId, Integer qty);
    void updateQty(Integer userId, Integer productId, int qty);
    void remove(Integer userId, Integer productId);
    void clear(Integer userId);
    
}
