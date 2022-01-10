package com.getir.company.onlinebookstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PaymentDetails {

	public int paymentType;

	public CreditCard creditCard;

}
