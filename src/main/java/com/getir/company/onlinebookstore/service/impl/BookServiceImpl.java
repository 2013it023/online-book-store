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

		List<BookDto> bookDtos = request.getBookDetails().stream().map(detail -> {
			return BookDto.builder().userId(Long.valueOf(tokenDto.getUserId())).id(String.valueOf(UUID.randomUUID()))
					.name(detail.getName()).description(detail.getDescription()).price(detail.getPrice())
					.authorName(detail.getAuthorName()).quantity(detail.getQuantity()).build();
		}).collect(Collectors.toList());

		List<BookDto> dbResponse = bookDao.addOrUpdateBooks(bookDtos);

		List<BookDetails> bookDetails = dbResponse.stream().map(detail -> {
			return BookDetails.builder().id(detail.getId()).name(detail.getName()).description(detail.getDescription())
					.price(detail.getPrice()).authorName(detail.getAuthorName()).quantity(detail.getQuantity()).build();
		}).collect(Collectors.toList());

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

		List<String> compositeKeyList = request.getBookDetails().stream().map(BookDetails::getId)
				.collect(Collectors.toList());

		List<BookDto> dbResponse = bookDao.addOrUpdateBooks(createdBookDetailsWithUpdatedValue(request.getBookDetails(),
				bookDao.getBooksByUserIdAndId(Long.valueOf(tokenDto.getUserId()), compositeKeyList),
				tokenDto.getUserId()));

		if (CollectionUtils.isEmpty(dbResponse)) {
			throw new OnlineBookStoreException(OnlineBookStoreConstant.BOOK_NOT_FOUND, "Book not found.");
		}

		List<BookDetails> bookDetails = dbResponse.stream().map(detail -> {
			return BookDetails.builder().id(detail.getId()).name(detail.getName()).description(detail.getDescription())
					.price(detail.getPrice()).authorName(detail.getAuthorName()).quantity(detail.getQuantity()).build();
		}).collect(Collectors.toList());

		response.setData(UpdateBookDetailsResponse.builder().bookDetails(bookDetails).build());
		response.setMessage(BannerMessage.builder().code(OnlineBookStoreConstant.UPDATED_SUCCESSFULLY).build());

		return response;
	}

	private List<BookDto> createdBookDetailsWithUpdatedValue(List<BookDetails> newValues, List<BookDto> dbResultList,
			String userId) {

		List<BookDto> results = new ArrayList<>();

		for (BookDto book : dbResultList) {
			BookDtoBuilder bookDto = BookDto.builder();
			for (BookDetails newBookDetails : newValues) {
				if (newBookDetails.getId().equals(book.getId())) {
					bookDto.id(book.getId());
					bookDto.userId(Long.valueOf(userId));
					if (StringUtils.isNotEmpty(newBookDetails.getName()))
						bookDto.name(newBookDetails.getName());
					if (StringUtils.isNotEmpty(newBookDetails.getDescription()))
						bookDto.description(newBookDetails.getDescription());
					if (StringUtils.isNotEmpty(newBookDetails.getAuthorName()))
						bookDto.authorName(newBookDetails.getAuthorName());
					if (newBookDetails.getPrice() >= 0)
						bookDto.price(newBookDetails.getPrice());
					if (newBookDetails.getQuantity() >= 0)
						bookDto.quantity(newBookDetails.getQuantity());
					break;
				}
			}
			results.add(bookDto.build());
		}
		return results;
	}

}
