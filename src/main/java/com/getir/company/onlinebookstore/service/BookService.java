package com.getir.company.onlinebookstore.service;

import com.getir.company.onlinebookstore.core.pojo.ServiceResponse;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.request.AddBookRequest;
import com.getir.company.onlinebookstore.request.UpdateBookDetailsRequest;
import com.getir.company.onlinebookstore.response.AddBookResponse;
import com.getir.company.onlinebookstore.response.UpdateBookDetailsResponse;

public interface BookService {

	ServiceResponse<AddBookResponse> addBook(TokenDto tokenDto, AddBookRequest request);

	ServiceResponse<UpdateBookDetailsResponse> updateBook(TokenDto tokenDto, UpdateBookDetailsRequest request);

}
