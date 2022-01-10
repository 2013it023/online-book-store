package com.getir.company.onlinebookstore.dao;

import java.time.LocalDateTime;
import java.util.List;

import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.dto.OrderDto;

/**
 * 
 * Provides abstract methods to interact with order tables.
 * 
 * @author Saravanan Perumal
 *
 */
public interface OrderDao {

	OrderDto createOrder(OrderDto orderDetails);

	OrderDto getOrderById(TokenDto tokenDto, String orderId);

	List<OrderDto> getOrderByUserId(Long userId);

	List<OrderDto> serachOrdersByYear(Integer year, Long userId);

	List<OrderDto> searchOrdersByInterval(LocalDateTime startTime, LocalDateTime endTime, Long userId);

}
