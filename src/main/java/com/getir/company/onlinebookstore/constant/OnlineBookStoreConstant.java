package com.getir.company.onlinebookstore.constant;

/**
 * 
 * OnlineBookStoreConstant - constant class for entire module
 * 
 * @author Saravanan Perumal
 *
 */
public class OnlineBookStoreConstant {

	public static final String JWT_TOKEN_COOKIE_NAME = "SSO_JWT";

	// Online Book Store Success Code
	public static final String NEW_PROFILE_SUCCESS = "1001";
	public static final String NEW_BOOKS_ADDED_SUCCESSFULLY = "1002";
	public static final String UPDATED_SUCCESSFULLY = "1003";
	public static final String ORDER_CREATED_SUCCESSFULLY = "1004";
	public static final String ORDER_NOT_FOUND_BY_SEARCH = "1005";

	// Online Book Store Error Code
	public static final String PROFILE_ALREADY_EXIST = "5001";
	public static final String ORDER_NOT_FOUND = "5003";
	public static final String BOOK_NOT_FOUND = "5004";
	public static final String INVALID_DETAILS = "5005";

	public static final String AUTHORISATION = "Authorization";
	public static final String TOKEN_PREFIX = "Bearer";
	public static final String JWT_ENABLED = "Y";






}
