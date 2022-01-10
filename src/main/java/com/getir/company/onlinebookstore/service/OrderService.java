package com.getir.company.onlinebookstore.service;

import org.javatuples.Pair;

import com.getir.company.onlinebookstore.core.pojo.ServiceResponse;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.request.OrderRequest;
import com.getir.company.onlinebookstore.response.OrderResponse;
import com.getir.company.onlinebookstore.response.OrdersByCustomerResponse;

public interface OrderService {

	ServiceResponse<OrderResponse> createOrder(TokenDto tokenDto, OrderRequest request);

	ServiceResponse<OrderResponse> getOrderById(TokenDto tokenDto, String orderId);

	ServiceResponse<OrdersByCustomerResponse> getOrdersByDates(TokenDto tokenDto, Pair<String, String> interval);

}
