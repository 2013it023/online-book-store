package com.getir.company.onlinebookstore.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.getir.company.onlinebookstore.constant.OnlineBookStoreConstant;
import com.getir.company.onlinebookstore.core.exception.OnlineBookStoreException;
import com.getir.company.onlinebookstore.core.pojo.BannerMessage;
import com.getir.company.onlinebookstore.core.pojo.ServiceResponse;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.dao.BookDao;
import com.getir.company.onlinebookstore.dto.BookDto;
import com.getir.company.onlinebookstore.dto.BookDto.BookDtoBuilder;
import com.getir.company.onlinebookstore.dto.BookStockInformationDto;
import com.getir.company.onlinebookstore.pojo.BookDetails;
import com.getir.company.onlinebookstore.request.AddBookRequest;
import com.getir.company.onlinebookstore.request.UpdateBookDetailsRequest;
import com.getir.company.onlinebookstore.response.AddBookResponse;
import com.getir.company.onlinebookstore.response.UpdateBookDetailsResponse;
import com.getir.company.onlinebookstore.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookDao bookDao;

	@Override
	public ServiceResponse<AddBookResponse> addBook(TokenDto tokenDto, AddBookRequest request) {
		// TODO Auto-generated method stub

		if (request != null && CollectionUtils.isEmpty(request.getBookDetails())) {
			throw new OnlineBookStoreException(OnlineBookStoreConstant.INVALID_DETAILS, "Invalid Information.");
		}

		ServiceResponse<AddBookResponse> response = new ServiceResponse<>();
		List<BookDto> bookDtos = new ArrayList<>();
		List<BookStockInformationDto> bookStockInformationDtos = new ArrayList<>();

		for (BookDetails detail : request.getBookDetails()) {

			String bookId = String.valueOf(UUID.randomUUID());
			bookDtos.add(BookDto.builder().userId(Long.valueOf(tokenDto.getUserId())).id(bookId).name(detail.getName())
					.description(detail.getDescription()).price(detail.getPrice()).authorName(detail.getAuthorName())
					.build());
			bookStockInformationDtos.add(BookStockInformationDto.builder().userId(Long.valueOf(tokenDto.getUserId()))
					.id(bookId).quantity(detail.getQuantity()).build());
		}

		List<BookDto> dbResponse = bookDao.addOrUpdateBooks(bookDtos);
		List<BookStockInformationDto> stockResponse = bookDao.insertBookStockInformation(bookStockInformationDtos);

		List<BookDetails> bookDetails = new ArrayList<>();
		for (int i = 0; i < dbResponse.size(); i++) {

			BookDto bookDto = dbResponse.get(i);
			BookStockInformationDto stockDto = stockResponse.get(i);
			bookDetails.add(BookDetails.builder().id(bookDto.getId()).name(bookDto.getName())
					.description(bookDto.getDescription()).price(bookDto.getPrice()).authorName(bookDto.getAuthorName())
					.quantity(stockDto.getQuantity()).build());

		}

		response.setData(AddBookResponse.builder().bookDetails(bookDetails).build());

		response.setMessage(BannerMessage.builder().code(OnlineBookStoreConstant.NEW_BOOKS_ADDED_SUCCESSFULLY).build());

		return response;
	}

	@Override
	public ServiceResponse<UpdateBookDetailsResponse> updateBook(TokenDto tokenDto, UpdateBookDetailsRequest request) {

		if (request != null && CollectionUtils.isEmpty(request.getBookDetails())) {
			throw new OnlineBookStoreException(OnlineBookStoreConstant.INVALID_DETAILS, "Invalid Information.");
		}

		ServiceResponse<UpdateBookDetailsResponse> response = new ServiceResponse<>();

		List<BookStockInformationDto> stockInformation = request.getBookDetails().stream().map(stockToUpdate -> {
			return BookStockInformationDto.builder().userId(Long.valueOf(tokenDto.getUserId()))
					.id(stockToUpdate.getId()).quantity(stockToUpdate.getQuantity()).build();
		}).collect(Collectors.toList());

		bookDao.insertBookStockInformation(stockInformation);

		response.setMessage(BannerMessage.builder().code(OnlineBookStoreConstant.UPDATED_SUCCESSFULLY).build());

		return response;
	}

}
