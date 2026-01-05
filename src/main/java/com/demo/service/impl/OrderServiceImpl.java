package com.demo.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.dao.CartDao;
import com.demo.dao.OrderDao;
import com.demo.dao.ProductDao;
import com.demo.entity.Cart;
import com.demo.entity.Order;
import com.demo.entity.OrderItem;
import com.demo.entity.OrderStatus;
import com.demo.entity.Product;
import com.demo.exception.BadRequestException;
import com.demo.exception.ForbiddenException;
import com.demo.exception.NotFoundException;
import com.demo.service.OrderService;
import com.demo.util.Require;
import com.demo.web.dto.order.OrderDetailDTO;
import com.demo.web.dto.order.OrderSummaryDTO;

@Service
public class OrderServiceImpl implements OrderService {

    private final CartDao cartDao;
    private final ProductDao productDao;
    private final OrderDao orderDao;

    public OrderServiceImpl(CartDao cartDao, ProductDao productDao, OrderDao orderDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
        this.orderDao = orderDao;
    }

    @Override
    @Transactional
    public Order placeOrder(Integer userId) {

        Integer uid = Require.positive(userId, "userId");

        Cart cart = cartDao.findByUserId(uid)
                .orElseThrow(() -> new NotFoundException("Cart not found for userId=" + uid));

        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());

        BigDecimal total = BigDecimal.ZERO;

        for (var ci : cart.getItems()) {
            int qty = ci.getQty();
            if (qty <= 0) continue;

            // LOCK product to avoid oversell
            var p = productDao.findByIdForUpdate(ci.getProduct().getId()).orElseThrow(
            		() -> new NotFoundException("Product not found"));

            if (!p.isActive()) throw new BadRequestException("Product is inactive: " + p.getId());
            
            if (p.getStock() == null || p.getStock() < qty) {
                throw new BadRequestException("Out of stock: " + p.getName() + " (stock=" + p.getStock() + ")");
            }

            // subtract stock
            p.setStock(p.getStock() - qty);

            // create order item
            OrderItem oi = new OrderItem();
            oi.setProduct(p);
            oi.setQty(qty);
            oi.setUnitPrice(p.getPrice()); // price snapshot at checkout
            order.addItem(oi);

            total = total.add(oi.getLineTotal());
        }

        if (order.getItems().isEmpty()) {
            throw new BadRequestException("Nothing to place (invalid cart items)");
        }

        order.setTotal(total);

        // persist order + items
        orderDao.save(order);

        // clear cart items (orphanRemoval => delete cart_items)
        cart.getItems().clear();

        return order;
    }

	@Override
	@Transactional(readOnly = true)
	public OrderDetailDTO getDetail(Integer userId, Integer orderId) {
	    Order o = orderDao.findByIdAndUserId(userId, orderId)
	        .orElseThrow(() -> new NotFoundException("Order not found"));
	    return OrderDetailDTO.toDTO(o);
	}


	@Override
	@Transactional(readOnly = true)
	public List<OrderSummaryDTO> listMyOrders(Integer userId) {
		Integer uId = Require.positive(userId, "userId");
		return orderDao.findSummariesByUserId(uId);
	}

	@Override
	@Transactional
	public void cancelOrder(Integer userId, Integer orderId) {
	    Integer uId = Require.positive(userId, "userId");
	    Integer oId = Require.positive(orderId, "orderId");

	    Order order = orderDao.findByIdAndUserId(uId, oId)
	            .orElseThrow(() -> new NotFoundException("Order not found: " + oId));

	    // 1) owner check
	    if (!order.getUser().getId().equals(uId)) {
	        throw new ForbiddenException("You are not allowed to cancel this order");
	    }

	    // 2) status rule (CHỈ PLACED)
	    if (!order.getStatus().canCancel()) {
	        throw new BadRequestException("Order cannot be cancelled in status: " + order.getStatus());
	    }

	    // 3) restore stock
	    for (OrderItem item : order.getItems()) {
	        Product p = item.getProduct();
	        p.setStock(p.getStock() + item.getQty());
	    }

	    // 4) update status + timestamp
	    order.setStatus(OrderStatus.CANCELED);
	    order.setCancelledAt(java.time.LocalDateTime.now());
	}


}
