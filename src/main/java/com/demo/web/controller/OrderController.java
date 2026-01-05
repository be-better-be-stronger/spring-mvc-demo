package com.demo.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.demo.security.AuthRequests;
import com.demo.service.CartService;
import com.demo.service.OrderService;
import com.demo.web.dto.CartView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;
	private final CartService cartService;

	public OrderController(OrderService orderService, CartService cartService) {
		this.orderService = orderService;
		this.cartService = cartService;
	}

	@GetMapping("/checkout")
	public String checkout(HttpServletRequest req, Model model) {
		Integer userId = AuthRequests.getUserId(req);
		System.out.println("userId auth: " + userId);
		CartView cartView = cartService.getView(userId);
		model.addAttribute("cart", cartView);
		return "order/checkout";
	}

	@PostMapping("/place")
	public String place(HttpServletRequest req) {
		Integer userId = AuthRequests.getUserId(req);
		var order = orderService.placeOrder(userId);
		return "redirect:/orders/" + order.getId();
	}

	@GetMapping
	public String myOrders(HttpServletRequest req, Model model) {
		Integer userId = AuthRequests.getUserId(req);
		model.addAttribute("orders", orderService.listMyOrders(userId));
		return "order/list";
	}

	@GetMapping("/{id}")
	public String detail(@PathVariable("id") Integer id, HttpServletRequest req, Model model) {
		Integer userId = AuthRequests.getUserId(req);
		var order = orderService.getDetail(userId, id);
		model.addAttribute("order", order);
		return "order/detail";
	}

	@PostMapping("/{id}/cancel")
	public String cancel(@PathVariable("id") Integer id, HttpServletRequest req) {
		Integer userId = AuthRequests.getUserId(req);
		orderService.cancelOrder(userId, id);
		return "redirect:/orders";
	}

}