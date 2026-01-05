package com.demo.service.impl;



import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.CartDao;
import com.demo.dao.ProductDao;
import com.demo.dao.UserDao;
import com.demo.entity.Cart;
import com.demo.entity.CartItem;
import com.demo.entity.Product;
import com.demo.entity.User;
import com.demo.exception.BadRequestException;
import com.demo.exception.NotFoundException;
import com.demo.service.CartService;
import com.demo.util.Require;
import com.demo.web.dto.CartView;

@Service
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;
    private final UserDao userDao;

    public CartServiceImpl(CartDao cartDao, ProductDao productDao, UserDao userDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional(readOnly = true)
    public CartView getView(Integer userId) {
    	Integer uId = Require.positive(userId, "userId");
        Cart cart = cartDao.findByUserIdWithItems(uId).orElse(null);

        CartView view = new CartView();
        if (cart == null) return view;

        for (var it : cart.getItems()) {
            var r = new CartView.Row();
            r.productId = it.getProduct().getId();
            r.productName = it.getProduct().getName();
            r.unitPrice = it.getUnitPrice();
            r.qty = it.getQty();
            view.getItems().add(r);
        }
        return view;
    }

    @Override
    @Transactional
    public void add(Integer userId, Integer productId, Integer qty) {  
    	
        Integer uId = Require.positive(userId, "userId");
        Integer pId = Require.positive(productId, "productId");
        Integer quantity = Require.positive(qty, "qty");

        Product p = productDao.findById(pId)
                .orElseThrow(() -> new NotFoundException("Product not found: " + pId));

        if (!p.isActive()) throw new BadRequestException("Product is inactive");
        if (p.getStock() == null || p.getStock() <= 0) throw new BadRequestException("Out of stock");

        Cart cart = getOrCreateCart(uId);

        int current = cart.getQtyOf(pId);
        int target = current + quantity;

        if (target > p.getStock()) {
            throw new BadRequestException("qty exceeds stock, stock=" + p.getStock());
        }

        cart.addOrInc(p, quantity);
        cartDao.save(cart);
    }

    @Override
    @Transactional
    public void updateQty(Integer userId, Integer productId, int qty) {
    	
        Cart cart = getOrCreateCart(userId);        
        Product p = productDao.findById(productId).orElseThrow(() -> new BadRequestException("Product not found"));

        CartItem item = cart.findItem(productId); // tìm item đã tồn tại
        if (item == null) throw new NotFoundException("Item not in cart");

       if(qty <= 0) cart.removeProduct(productId);       
       else if (qty > p.getStock()) throw new BadRequestException("Out of stock");
       else item.setQty(qty);
    }

    @Override
    @Transactional
    public void remove(Integer userId, Integer productId) {
        Cart cart = getOrCreateCart(userId);
        cart.removeProduct(productId);
        cartDao.save(cart);
    }

    @Override
    @Transactional
    public void clear(Integer userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear(); // orphanRemoval => delete rows
        cartDao.save(cart);
    }

    private Cart getOrCreateCart(Integer userId) {
    	Integer uId = Require.positive(userId, "userId");
        return cartDao.findByUserIdWithItems(uId)
                .orElseGet(() -> {
                    User u = userDao.findById(uId)
                            .orElseThrow(() -> new NotFoundException("User not found: " + uId));
                    Cart c = new Cart();
                    c.setUser(u);
                    return cartDao.save(c);
                });
    }

	
}
