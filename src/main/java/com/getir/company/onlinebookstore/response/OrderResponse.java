package com.getir.company.onlinebookstore.response;

import java.util.List;

import com.getir.company.onlinebookstore.dto.BookDetails;
import com.getir.company.onlinebookstore.dto.PaymentInformation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {

	public String orderId;

	public String orderedDate;
	
	public int paymentType;

	public List<BookDetails> bookDetails;

	public PaymentInformation paymentDetails;

}
