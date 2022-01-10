package com.getir.company.onlinebookstore.core.exception;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.getir.company.onlinebookstore.config.BookStoreConfig;
import com.getir.company.onlinebookstore.core.pojo.BannerMessage;
import com.getir.company.onlinebookstore.core.pojo.ErrorInfo;
import com.getir.company.onlinebookstore.core.pojo.OnlineBookStoreResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * ExceptionHandlers class handles the exception caught under com.getir.company
 * packages.
 * 
 * @author Saravanan Perumal
 *
 */
@RestControllerAdvice(basePackages = "com.getir.company")
@Slf4j
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

	@Autowired
	private BookStoreConfig messagePropertiesConfig;

	@Autowired
	Environment env;

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

	/**
	 * handleServiceException will be executed when OnlineBookStoreException.class
	 * throw at service level
	 * 
	 * @param servletRequest
	 * @param exception
	 * @param webRequest
	 * @return error response
	 */
	@ExceptionHandler(OnlineBookStoreException.class)
	public OnlineBookStoreResponse<Object> handleServiceException(HttpServletRequest servletRequest,
			OnlineBookStoreException exception, WebRequest webRequest) {

		OnlineBookStoreResponse<Object> response = buildServiceResponse(servletRequest, exception);

		return response;
	}

	private OnlineBookStoreResponse<Object> buildServiceResponse(HttpServletRequest servletRequest,
			OnlineBookStoreException exception) {

		Map<String, String> messageMap = messagePropertiesConfig.getMessage();

		log.error(exception.getErrorMessage());

		OnlineBookStoreResponse<Object> response = new OnlineBookStoreResponse<>();

		ErrorInfo errorInfo = ErrorInfo.builder().e2eRequestId(servletRequest.getHeader("e2eRequestId"))
				.errorCode(exception.getErrorCode()).errorMessage(exception.getErrorMessage()).errorName("ERROR")
				.timeStamp(LocalDateTime.now().format(formatter)).build();

		response.setErrorInfo(errorInfo);

		response.setBannerMessage(BannerMessage.builder().code(exception.getErrorCode())
				.message(messageMap.get(exception.getErrorCode())).type("ERROR").build());

		return response;
	}

	@ExceptionHandler(RuntimeException.class)
	public OnlineBookStoreResponse<Object> handleServiceException(HttpServletRequest servletRequest,
			Exception exception, WebRequest webRequest) {
		
		OnlineBookStoreResponse<Object> response = new OnlineBookStoreResponse<>();

		Map<String, String> messageMap = messagePropertiesConfig.getMessage();
		
		ErrorInfo errorInfo = ErrorInfo.builder().e2eRequestId(servletRequest.getHeader("e2eRequestId"))
				.errorCode("99").errorMessage(exception.getMessage()).errorName("ERROR")
				.timeStamp(LocalDateTime.now().format(formatter)).build();
		
		response.setErrorInfo(errorInfo);
		
		response.setBannerMessage(BannerMessage.builder().code("99")
				.message(messageMap.get("5002")).type("ERROR").build());

		return response;
	}

}
