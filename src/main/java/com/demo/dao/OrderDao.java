package com.demo.dao;

import java.util.List;
import java.util.Optional;

import com.demo.entity.Order;
import com.demo.web.dto.order.OrderSummaryDTO;

public interface OrderDao {
	
	Order save(Order order);
    Optional<Order> findByIdAndUserId(Integer userId, Integer id);
    List<OrderSummaryDTO> findSummariesByUserId(Integer userId);
}
