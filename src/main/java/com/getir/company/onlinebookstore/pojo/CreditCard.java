package com.getir.company.onlinebookstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditCard {

	public String encryptedCardNumber;

	public String encryptedSecurityCode;

	public int expiryMonth;

	public int expiryYear;

	public String nickName;
}
