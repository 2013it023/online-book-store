package com.getir.company.onlinebookstore.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.getir.company.onlinebookstore.constant.OnlineBookStoreConstant;
import com.getir.company.onlinebookstore.core.exception.OnlineBookStoreException;
import com.getir.company.onlinebookstore.core.pojo.BannerMessage;
import com.getir.company.onlinebookstore.core.pojo.ServiceResponse;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.dao.BookDao;
import com.getir.company.onlinebookstore.dao.OrderDao;
import com.getir.company.onlinebookstore.dto.BookDetails;
import com.getir.company.onlinebookstore.dto.BookStockInformationDto;
import com.getir.company.onlinebookstore.dto.OrderDto;
import com.getir.company.onlinebookstore.dto.OrderDto.OrderDtoBuilder;
import com.getir.company.onlinebookstore.dto.PaymentInformation;
import com.getir.company.onlinebookstore.pojo.PaymentDetails;
import com.getir.company.onlinebookstore.request.OrderRequest;
import com.getir.company.onlinebookstore.response.OrderResponse;
import com.getir.company.onlinebookstore.response.OrdersByCustomerResponse;
import com.getir.company.onlinebookstore.service.OrderService;
import com.getir.company.onlinebookstore.util.OnlineBookStoreUtil;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private BookDao bookDao;

	@Override
	public ServiceResponse<OrderResponse> createOrder(TokenDto tokenDto, OrderRequest request) {

		ServiceResponse<OrderResponse> response = new ServiceResponse<>();

		List<BookStockInformationDto> exisingStockInfos = validateStockAvailableInformation(request, tokenDto);

		OrderDtoBuilder orderDto = OrderDto.builder();
		PaymentDetails paymentDetails = request.getPaymentDetails();

		orderDto.userId(Long.valueOf(tokenDto.getUserId()));
		orderDto.orderedDate(LocalDateTime.now());
		orderDto.orderId(String.valueOf(UUID.randomUUID()));
		orderDto.totalAmount(request.getTotalAmount());
		orderDto.year(OnlineBookStoreUtil.getYear());
		orderDto.month(OnlineBookStoreUtil.getMonth());

		List<BookDetails> bookDetails = request.getBookDetails().stream().map(detail -> {
			return BookDetails.builder().bookId(detail.getId()).name(detail.getName()).quantity(detail.getQuantity())
					.build();
		}).collect(Collectors.toList());
		orderDto.bookDetails(bookDetails);

		orderDto.paymentType(paymentDetails.getPaymentType());
		if (paymentDetails != null && paymentDetails.getPaymentType() == 1) {
			orderDto.paymentInformation(PaymentInformation.builder()
					.cardNumber(OnlineBookStoreUtil
							.getCardNumberLast4Digits(paymentDetails.getCreditCard().getEncryptedCardNumber()))
					.nickName(paymentDetails.getCreditCard().getNickName()).build());
		}

		OrderDto result = orderDao.createOrder(orderDto.build());

		response.setData(OnlineBookStoreUtil.createOrderResponse(result));

		response.setMessage(BannerMessage.builder().code(OnlineBookStoreConstant.ORDER_CREATED_SUCCESSFULLY).build());

		updateStockInformation(request.getBookDetails(), exisingStockInfos);

		return response;
	}

	private void updateStockInformation(List<com.getir.company.onlinebookstore.pojo.BookDetails> bookDetails,
			List<BookStockInformationDto> exisingStockInfos) {

		List<BookStockInformationDto> updatedDto = new ArrayList<>();
		for (int i = 0; i < exisingStockInfos.size(); i++) {
			com.getir.company.onlinebookstore.pojo.BookDetails detail = bookDetails.get(i);
			BookStockInformationDto informationDto = exisingStockInfos.get(i);
			informationDto.setQuantity(informationDto.getQuantity() - detail.getQuantity());
			updatedDto.add(informationDto);
		}

		bookDao.insertBookStockInformation(updatedDto);
	}

	private List<BookStockInformationDto> validateStockAvailableInformation(OrderRequest request, TokenDto tokenDto) {

		List<BookStockInformationDto> stockDetailsByKey = bookDao.getStockDetailsByKey(
				request.getBookDetails().stream().map(detail -> detail.getId()).collect(Collectors.toList()));

		stockDetailsByKey.stream().forEach(detail -> {
			if (detail.getQuantity() <= 0)
				throw new OnlineBookStoreException(OnlineBookStoreConstant.STOCK_NOT_AVAILABLE, "Stock not available");
		});

		return stockDetailsByKey;
	}

	@Override
	public ServiceResponse<OrderResponse> getOrderById(TokenDto tokenDto, String orderId) {
		ServiceResponse<OrderResponse> response = new ServiceResponse<>();
		OrderDto result = orderDao.getOrderById(tokenDto, orderId);

		if (Objects.isNull(result)) {
			throw new OnlineBookStoreException(OnlineBookStoreConstant.ORDER_NOT_FOUND, "Order not found.");
		}

		response.setData(OnlineBookStoreUtil.createOrderResponse(result));

		return response;
	}

	@Override
	public ServiceResponse<OrdersByCustomerResponse> getOrdersByDates(TokenDto tokenDto,
			Pair<String, String> interval) {

		ServiceResponse<OrdersByCustomerResponse> response = new ServiceResponse<>();

		List<OrderDto> orderDtos = orderDao.searchOrdersByInterval(
				OnlineBookStoreUtil.convertDateToLocalDateTime(interval.getValue0(), true),
				OnlineBookStoreUtil.convertDateToLocalDateTime(interval.getValue0(), false),
				Long.valueOf(tokenDto.getUserId()));

		if (Objects.isNull(orderDtos)) {
			throw new OnlineBookStoreException(OnlineBookStoreConstant.ORDER_NOT_FOUND, "Order not found.");
		}

		List<OrderResponse> ordersList = orderDtos.stream().map(order -> {
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
