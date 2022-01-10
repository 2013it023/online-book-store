package com.getir.company.onlinebookstore.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.function.BiFunction;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.getir.company.onlinebookstore.config.BookStoreConfig;
import com.getir.company.onlinebookstore.core.pojo.BannerMessage;
import com.getir.company.onlinebookstore.core.pojo.ErrorInfo;
import com.getir.company.onlinebookstore.core.pojo.OnlineBookStoreResponse;
import com.getir.company.onlinebookstore.core.pojo.ServiceResponse;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;

/**
 * 
 * Abstract controller to provide a common place to inteact with service layer
 * and return unified response structure.
 * 
 * @author Saravanan Perumal
 *
 */
public abstract class IRestController {

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

	@Autowired
	BookStoreConfig config;

	@Autowired
	Environment en;

	protected <R, T> ResponseEntity<OnlineBookStoreResponse<T>> invokeServiceMethod(HttpServletRequest servletRequest,
			R clientRequest, BiFunction<TokenDto, R, ServiceResponse<T>> serviceMethod) {

		TokenDto tokenDto = createRequestContext(servletRequest);
		ServiceResponse<T> response = serviceMethod.apply(tokenDto, clientRequest);
		return CreateResponse(servletRequest, response);
	}

	private <T> ResponseEntity<OnlineBookStoreResponse<T>> CreateResponse(HttpServletRequest servletRequest,
			ServiceResponse<T> response) {

		OnlineBookStoreResponse<T> bookStoreResponse = new OnlineBookStoreResponse<>();

		bookStoreResponse.setData(response.getData());

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorCode("00");
		errorInfo.setErrorMessage("SUCCESS");
		errorInfo.setErrorName(HttpStatus.OK.name());
		errorInfo.setTimeStamp(LocalDateTime.now().format(formatter));
		errorInfo.setE2eRequestId(servletRequest.getHeader("e2eRequestId"));

		bookStoreResponse.setErrorInfo(errorInfo);

		if (response.getMessage() != null) {
			bookStoreResponse.setBannerMessage(createBannerMessage(response.getMessage()));
		}

		System.out.println(config.getMessage().size());

		return new ResponseEntity<>(bookStoreResponse, HttpStatus.OK);

	}

	private BannerMessage createBannerMessage(BannerMessage message) {
		Map<String, String> messageMap = config.getMessage();
		return BannerMessage.builder().code(message.getCode()).type("SUCCESS")
				.message(messageMap.get(message.getCode())).build();
	}

	private TokenDto createRequestContext(HttpServletRequest servletRequest) {

		String userId = servletRequest.getHeader("userId");

		TokenDto tokenDto = new TokenDto();

		tokenDto.setUserId(
				StringUtils.isEmpty(userId) ? String.valueOf(servletRequest.getAttribute("userId")) : userId);

		return tokenDto;
	}

}
