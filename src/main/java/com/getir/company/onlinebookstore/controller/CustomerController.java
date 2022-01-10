package com.getir.company.onlinebookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getir.company.onlinebookstore.core.pojo.OnlineBookStoreResponse;
import com.getir.company.onlinebookstore.request.NewCustomerRequest;
import com.getir.company.onlinebookstore.response.NewCustomerResponse;
import com.getir.company.onlinebookstore.response.OrdersByCustomerResponse;
import com.getir.company.onlinebookstore.service.CustomerService;

/**
 * Customer controller will serve request related to profile.
 * 
 * @author Saravanan Perumal
 *
 */
@RestController
@RequestMapping("/getir/gw/store/customer")
public class CustomerController extends IRestController {

	@Autowired
	private CustomerService service;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OnlineBookStoreResponse<NewCustomerResponse>> addCustomer(HttpServletRequest servletRequest,
			@RequestBody(required = true) NewCustomerRequest request) {
		return invokeServiceMethod(servletRequest, request, service::addCustomer);
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OnlineBookStoreResponse<OrdersByCustomerResponse>> getAllOrders(
			HttpServletRequest servletRequest, @RequestParam(name = "from", required = true) String from) {
		return invokeServiceMethod(servletRequest, from, service::getOrdersByUserId);
	}

}
