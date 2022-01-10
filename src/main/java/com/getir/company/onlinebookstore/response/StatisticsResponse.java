package com.getir.company.onlinebookstore.response;

import java.util.Map;

import com.getir.company.onlinebookstore.pojo.StatisticsDetails;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatisticsResponse {
	
	public Map<String, StatisticsDetails> reports;

}
