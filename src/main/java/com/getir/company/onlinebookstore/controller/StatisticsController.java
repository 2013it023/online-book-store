package com.getir.company.onlinebookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.getir.company.onlinebookstore.core.pojo.OnlineBookStoreResponse;
import com.getir.company.onlinebookstore.response.StatisticsResponse;
import com.getir.company.onlinebookstore.service.StatisticsService;

/**
 * stats controller will serve request related to staticstics
 * 
 * @author Saravanan Perumal
 *
 */
@RestController
@RequestMapping("/getir/gw/store/stats")
public class StatisticsController extends IRestController {

	@Autowired
	private StatisticsService service;

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OnlineBookStoreResponse<StatisticsResponse>> getCustomerYearlyReport(
			HttpServletRequest servletRequest, @RequestParam(name = "year", required = true) String year) {
		return invokeServiceMethod(servletRequest, year, service::getCustomerYearlyReport);
	}
}
