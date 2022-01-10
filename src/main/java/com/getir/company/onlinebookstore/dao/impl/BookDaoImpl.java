package com.getir.company.onlinebookstore.dao.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

import com.getir.company.onlinebookstore.dao.BookDao;
import com.getir.company.onlinebookstore.dto.BookDto;
import com.getir.company.onlinebookstore.dto.BookStockInformationDto;

/**
 * 
 * Provides implementation for the BookDao
 * 
 * @author Saravanan Perumal
 *
 */
@Repository
public class BookDaoImpl implements BookDao {

	@Autowired
	private CassandraOperations cassandraTemplate;

	@Override
	public List<BookDto> addOrUpdateBooks(List<BookDto> bookList) {

		return bookList.stream().map(book -> {
			return cassandraTemplate.insert(book);
		}).collect(Collectors.toList());
	}

	@Override
	public List<BookDto> getBooksByUserIdAndId(Long userId, List<String> compositeKeyList) {
		return compositeKeyList.stream().map(compositeKey -> {
			return cassandraTemplate.selectOne(Query.query(Criteria.where("userid").is(userId))
					.and(Criteria.where("id").is(compositeKey)).withAllowFiltering(), BookDto.class);
		}).collect(Collectors.toList());
	}

	@Override
	public List<BookStockInformationDto> insertBookStockInformation(
			List<BookStockInformationDto> bookStockInformation) {
		return bookStockInformation.stream().map(stockDetail -> {
			return cassandraTemplate.insert(stockDetail);
		}).collect(Collectors.toList());
	}

	@Override
	public List<BookStockInformationDto> getStockDetailsByKey(List<String> indexKeysList) {
		return indexKeysList.stream().map(key -> {
			return cassandraTemplate.selectOne(Query.query(Criteria.where("id").is(key)),
					BookStockInformationDto.class);
		}).collect(Collectors.toList());
	}

}
