package com.demo.web.auth;

import com.demo.security.SessionKeys;
import com.demo.service.CartService;
import com.demo.web.session.PendingAddToCart;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class PostLoginHandler {

    private final CartService cartService;

    public PostLoginHandler(CartService cartService) {
        this.cartService = cartService;
    }

    /**
     * @return redirect target (relative path)
     */
    public String handle(HttpSession session, Integer userId) {
        // 1) Pending add to cart
        PendingAddToCart pending =
                (PendingAddToCart) session.getAttribute(SessionKeys.PENDING_ADD_TO_CART);

        if (pending != null) {
            session.removeAttribute(SessionKeys.PENDING_ADD_TO_CART);
            cartService.add(userId, pending.getProductId(), pending.getQty());
            return "/cart";
        }

        // 2) Pending place order
        String pendingPlaceOrder =
                (String) session.getAttribute(SessionKeys.PENDING_PLACE_ORDER);

        if (pendingPlaceOrder != null) {
            session.removeAttribute(SessionKeys.PENDING_PLACE_ORDER);
            return "/orders/checkout";
        }

        // 3) Normal after-login
        String after =
                (String) session.getAttribute(SessionKeys.AFTER_LOGIN_URL);
        session.removeAttribute(SessionKeys.AFTER_LOGIN_URL);

        return after;
    }
}
