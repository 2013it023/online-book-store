package com.getir.company.onlinebookstore.service;

import com.getir.company.onlinebookstore.core.pojo.ServiceResponse;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.request.NewCustomerRequest;
import com.getir.company.onlinebookstore.response.NewCustomerResponse;
import com.getir.company.onlinebookstore.response.OrdersByCustomerResponse;

public interface CustomerService {

	ServiceResponse<NewCustomerResponse> addCustomer(TokenDto tokenDto, NewCustomerRequest request);
	
	ServiceResponse<OrdersByCustomerResponse> getOrdersByUserId(TokenDto tokenDto, String from);

}
