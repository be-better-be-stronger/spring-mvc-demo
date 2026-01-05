package com.demo.service;

import java.util.List;

import com.demo.entity.Order;
import com.demo.web.dto.order.OrderDetailDTO;
import com.demo.web.dto.order.OrderSummaryDTO;

public interface OrderService {
	Order placeOrder(Integer userId);
	
    OrderDetailDTO getDetail(Integer userId, Integer orderId);

    List<OrderSummaryDTO> listMyOrders(Integer userId);
//    long countMyOrders(Integer userId);
    
    void cancelOrder(Integer userId, Integer orderId);
}
