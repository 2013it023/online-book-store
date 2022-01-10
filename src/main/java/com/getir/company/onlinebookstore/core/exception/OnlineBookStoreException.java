package com.getir.company.onlinebookstore.core.exception;

import lombok.Getter;

/**
 * 
 * OnlineBookStoreException - Common exception class for entire module.
 * 
 * @author Saravanan Perumal
 *
 */
@Getter
public class OnlineBookStoreException extends RuntimeException {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String errorCode;

	private String errorMessage;

	public OnlineBookStoreException(String errorCode) {
		this.errorCode = errorCode;
	}

	public OnlineBookStoreException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

	public OnlineBookStoreException(String errorCode, String errorMessage, Throwable t) {
		super(errorMessage, t);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}

}
