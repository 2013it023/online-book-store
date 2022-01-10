package com.getir.company.onlinebookstore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.getir.company.onlinebookstore.constant.OnlineBookStoreConstant;
import com.getir.company.onlinebookstore.core.pojo.BannerMessage;
import com.getir.company.onlinebookstore.core.pojo.ServiceResponse;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.dao.OrderDao;
import com.getir.company.onlinebookstore.dto.OrderDto;
import com.getir.company.onlinebookstore.pojo.StatisticsDetails;
import com.getir.company.onlinebookstore.response.StatisticsResponse;
import com.getir.company.onlinebookstore.service.StatisticsService;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private OrderDao orderDao;

	@Override
	public ServiceResponse<StatisticsResponse> getCustomerYearlyReport(TokenDto tokenDto, String year) {

		Map<String, StatisticsDetails> statisticMap = new HashMap<>();
		ServiceResponse<StatisticsResponse> response = new ServiceResponse<>();

		List<OrderDto> orderDtos = orderDao.serachOrdersByYear(Integer.valueOf(year),
				Long.valueOf(tokenDto.getUserId()));

		for (OrderDto order : orderDtos) {

			if (!statisticMap.containsKey(order.getMonth())) {
				statisticMap.put(order.getMonth(),
						StatisticsDetails.builder().orderCount(1).totalBookCount(order.getBookDetails().size())
								.totalPurchasedAmount(order.getTotalAmount()).build());
			} else {
				StatisticsDetails detail = statisticMap.get(order.getMonth());
				long prevOrderCount = detail.getOrderCount();
				int prevBookCount = detail.getTotalBookCount();
				float prevPurchasedAmount = detail.getTotalPurchasedAmount();

				statisticMap.put(order.getMonth(),
						StatisticsDetails.builder().orderCount(prevOrderCount + 1)
								.totalBookCount(prevBookCount + order.getBookDetails().size())
								.totalPurchasedAmount(Float.sum(prevPurchasedAmount, order.getTotalAmount())).build());
			}

		}

		if (statisticMap.isEmpty()) {
			response.setMessage(
					BannerMessage.builder().code(OnlineBookStoreConstant.ORDER_NOT_FOUND_BY_SEARCH).build());
		}

		response.setData(StatisticsResponse.builder().reports(statisticMap).build());

		return response;
	}

}
