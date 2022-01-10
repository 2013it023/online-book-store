package com.getir.company.onlinebookstore.service;

import com.getir.company.onlinebookstore.core.pojo.ServiceResponse;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.response.StatisticsResponse;

public interface StatisticsService {

	ServiceResponse<StatisticsResponse> getCustomerYearlyReport(TokenDto tokenDto, String year);

}
