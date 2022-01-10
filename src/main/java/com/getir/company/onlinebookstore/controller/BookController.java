package com.getir.company.onlinebookstore.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.getir.company.onlinebookstore.core.pojo.OnlineBookStoreResponse;
import com.getir.company.onlinebookstore.request.AddBookRequest;
import com.getir.company.onlinebookstore.request.UpdateBookDetailsRequest;
import com.getir.company.onlinebookstore.response.AddBookResponse;
import com.getir.company.onlinebookstore.response.UpdateBookDetailsResponse;
import com.getir.company.onlinebookstore.service.BookService;

/**
 * Book controller will serve the requests related books.
 * 
 * @author Saravanan Perumal
 *
 */
@RestController
@RequestMapping("/getir/gw/store/book")
public class BookController extends IRestController {

	@Autowired
	private BookService bookService;

	@RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OnlineBookStoreResponse<AddBookResponse>> addBook(HttpServletRequest servletRequest,
			@RequestBody(required = true) AddBookRequest request) {

		return invokeServiceMethod(servletRequest, request, bookService::addBook);

	}

	@RequestMapping(method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<OnlineBookStoreResponse<UpdateBookDetailsResponse>> updateBook(
			HttpServletRequest servletRequest, @RequestBody(required = true) UpdateBookDetailsRequest request) {
		return invokeServiceMethod(servletRequest, request, bookService::updateBook);
	}

}
