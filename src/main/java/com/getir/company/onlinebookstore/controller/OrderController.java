package com.getir.company.onlinebookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getir.company.onlinebookstore.core.pojo.OnlineBookStoreResponse;
import com.getir.company.onlinebookstore.request.OrderRequest;
import com.getir.company.onlinebookstore.response.OrderResponse;
import com.getir.company.onlinebookstore.response.OrdersByCustomerResponse;
import com.getir.company.onlinebookstore.service.OrderService;

/**
 * Order controller will serve the request related to orders.
 * 
 * @author Saravanan Perumal
 *
 */
@RestController
@RequestMapping("/getir/gw/store/order")
public class OrderController extends IRestController {

	@Autowired
	private OrderService orderService;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OnlineBookStoreResponse<OrderResponse>> createOrder(HttpServletRequest servletRequest,
			@RequestBody(required = true) OrderRequest request) {
		return invokeServiceMethod(servletRequest, request, orderService::createOrder);
	}

	@RequestMapping(path = "/{orderId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OnlineBookStoreResponse<OrderResponse>> getOrderById(HttpServletRequest servletRequest,
			@PathVariable String orderId) {
		return invokeServiceMethod(servletRequest, orderId, orderService::getOrderById);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OnlineBookStoreResponse<OrdersByCustomerResponse>> getOrdersByDates(
			HttpServletRequest servletRequest, @RequestParam(required = true) String from,
			@RequestParam(required = true) String to) {

		return invokeServiceMethod(servletRequest, Pair.with(from, to), orderService::getOrdersByDates);
	}
}
