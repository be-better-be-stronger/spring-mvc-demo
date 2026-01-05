package com.demo.web.controller;

import com.demo.security.AuthRequests;
import com.demo.service.CartService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public String view(HttpServletRequest req, Model model) {
        Integer userId = AuthRequests.getUserId(req);
        model.addAttribute("cart", cartService.getView(userId));
        return "cart/view";
    }

    @PostMapping("/add")
    public String add(@RequestParam("productId") Integer productId,
                      @RequestParam(value = "qty", defaultValue = "1") Integer qty,
                      HttpServletRequest req) {
        Integer userId = AuthRequests.getUserId(req);
        cartService.add(userId, productId, qty);

        return "redirect:/cart";

    }

    @PostMapping("/update")
    public String update(@RequestParam("productId") Integer productId,
                         @RequestParam("qty") Integer qty,
                         HttpServletRequest req) {
        Integer userId = AuthRequests.getUserId(req);
        cartService.updateQty(userId, productId, qty);
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("productId") Integer productId,
                         HttpServletRequest req) {
        Integer userId = AuthRequests.getUserId(req);
        cartService.remove(userId, productId);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clear(HttpServletRequest req) {
        Integer userId = AuthRequests.getUserId(req);
        cartService.clear(userId);
        return "redirect:/cart";
    }

    
}
