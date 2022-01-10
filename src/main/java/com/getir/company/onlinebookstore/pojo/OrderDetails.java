package com.getir.company.onlinebookstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetails {

	public String bookId;

	public String bookName;

	public String orderedDate;

	public boolean deliveryStatus;

	public String bookPrice;

	public long quanity;

	public String orderId;

	public int paymentType;
}
