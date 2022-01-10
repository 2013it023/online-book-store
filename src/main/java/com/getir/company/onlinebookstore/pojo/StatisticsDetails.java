package com.getir.company.onlinebookstore.pojo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class StatisticsDetails {

	public long orderCount;

	public int totalBookCount;

	public float totalPurchasedAmount;

}
