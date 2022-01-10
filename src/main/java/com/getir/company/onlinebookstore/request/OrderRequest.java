package com.getir.company.onlinebookstore.request;

import java.util.List;

import com.getir.company.onlinebookstore.pojo.BookDetails;
import com.getir.company.onlinebookstore.pojo.PaymentDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	public float totalAmount;

	public List<BookDetails> bookDetails;

	public PaymentDetails paymentDetails;

}
