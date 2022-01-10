package com.getir.company.onlinebookstore.response;

import lombok.NoArgsConstructor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrdersByCustomerResponse {

	public List<OrderResponse> orderDetails;
}
