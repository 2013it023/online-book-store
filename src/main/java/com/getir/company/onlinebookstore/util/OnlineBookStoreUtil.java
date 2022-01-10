package com.getir.company.onlinebookstore.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import com.getir.company.onlinebookstore.dto.OrderDto;
import com.getir.company.onlinebookstore.response.OrderResponse;

public class OnlineBookStoreUtil {

	public static String getCurrentDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return String.valueOf(dtf.format(now));
	}

	public static String convertTimeStampToLocalDateTime(String timestamp) {
		Timestamp stamp = new Timestamp(Long.valueOf(timestamp));
		return stamp.toLocalDateTime().toString();
	}

	public static OrderResponse createOrderResponse(OrderDto result) {
		return OrderResponse.builder().bookDetails(result.getBookDetails()).orderId(result.getOrderId())
				.paymentDetails(result.getPaymentInformation()).paymentType(result.getPaymentType())
				.orderedDate(String.valueOf(result.getOrderedDate())).build();
	}

	public static String getCardNumberLast4Digits(String encryptedCardNumber) {
		String last4Digits = encryptedCardNumber.substring(encryptedCardNumber.length() - 4);
		return String.valueOf(new StringBuilder().append("*******").append(last4Digits));
	}

	public static int getYear() {
		return Calendar.getInstance().get(Calendar.YEAR);

	}

	public static String getMonth() {
		Calendar cal = Calendar.getInstance();
		return new SimpleDateFormat("MMM").format(cal.getTime());
	}

	public static LocalDateTime convertDateToLocalDateTime(String date, boolean start) {
		DateTimeFormatter DATEFORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate ld = LocalDate.parse(date, DATEFORMATTER);
		LocalDateTime ldt = LocalDateTime.of(ld, start ? LocalTime.MIN : LocalTime.MAX);
		return ldt;
	}
}
