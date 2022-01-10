package com.getir.company.onlinebookstore.dao;

import java.util.List;

import com.getir.company.onlinebookstore.dto.BookDto;

/**
 * Provides abstract methods to interact with Book_Dto
 * 
 * @author Saravanan Perumal
 *
 */
public interface BookDao {

	List<BookDto> addOrUpdateBooks(List<BookDto> bookList);

	List<BookDto> getBooksByUserIdAndId(Long userId, List<String> compositeKeyList);

}
