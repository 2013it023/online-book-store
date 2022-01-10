package com.getir.company.onlinebookstore.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.getir.company.onlinebookstore.constant.OnlineBookStoreConstant;
import com.getir.company.onlinebookstore.core.exception.OnlineBookStoreException;
import com.getir.company.onlinebookstore.core.pojo.BannerMessage;
import com.getir.company.onlinebookstore.core.pojo.ServiceResponse;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.dao.OrderDao;
import com.getir.company.onlinebookstore.dao.ProfileDao;
import com.getir.company.onlinebookstore.dto.OrderDto;
import com.getir.company.onlinebookstore.dto.ProfileDto;
import com.getir.company.onlinebookstore.request.NewCustomerRequest;
import com.getir.company.onlinebookstore.response.NewCustomerResponse;
import com.getir.company.onlinebookstore.response.OrderResponse;
import com.getir.company.onlinebookstore.response.OrdersByCustomerResponse;
import com.getir.company.onlinebookstore.service.CustomerService;
import com.getir.company.onlinebookstore.util.OnlineBookStoreUtil;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private ProfileDao profileDao;

	@Autowired
	private OrderDao orderDao;

	@Override
	public ServiceResponse<NewCustomerResponse> addCustomer(TokenDto tokenDto, NewCustomerRequest request) {

		ServiceResponse<NewCustomerResponse> serviceResponse = new ServiceResponse<>();

		ProfileDto profileDto = ProfileDto.builder().mobileNumber(request.getMobileNumber())
				.firstName(request.getFirstName()).lastName(request.getLastName()).emailId(request.getEmailId())
				.createdAt(OnlineBookStoreUtil.getCurrentDate()).build();

		boolean isProfileCreated = profileDao.save(profileDto);

		if (isProfileCreated) {
			serviceResponse
					.setMessage(BannerMessage.builder().code(OnlineBookStoreConstant.NEW_PROFILE_SUCCESS).build());

		} else {
			throw new OnlineBookStoreException(OnlineBookStoreConstant.PROFILE_ALREADY_EXIST, "Profile Already Exis.");
		}

		return serviceResponse;
	}

	@Override
	public ServiceResponse<OrdersByCustomerResponse> getOrdersByUserId(TokenDto tokenDto, String from) {

		ServiceResponse<OrdersByCustomerResponse> response = new ServiceResponse<>();

		List<OrderDto> result = orderDao.getOrderByUserId(Long.valueOf(tokenDto.getUserId()));

		if (Objects.isNull(result)) {
			throw new OnlineBookStoreException(OnlineBookStoreConstant.ORDER_NOT_FOUND, "Order not found.");
		}

		List<OrderResponse> ordersList = result.stream().map(order -> {
			return OnlineBookStoreUtil.createOrderResponse(order);
		}).collect(Collectors.toList());

		response.setData(OrdersByCustomerResponse.builder().orderDetails(ordersList).build());

		if (CollectionUtils.isEmpty(ordersList)) {
			response.setMessage(
					BannerMessage.builder().code(OnlineBookStoreConstant.ORDER_NOT_FOUND_BY_SEARCH).build());
		}

		return response;
	}

}
