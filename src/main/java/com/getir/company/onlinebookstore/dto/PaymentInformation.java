package com.getir.company.onlinebookstore.dto;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@UserDefinedType("payment_information")
public class PaymentInformation implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column("card_number")
	private String cardNumber;

	@Column("nick_name")
	private String nickName;

}
