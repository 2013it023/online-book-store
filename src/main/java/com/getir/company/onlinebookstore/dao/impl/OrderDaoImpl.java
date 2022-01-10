package com.getir.company.onlinebookstore.dao.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.stereotype.Repository;

import com.datastax.oss.driver.api.core.cql.Statement;
import com.getir.company.onlinebookstore.core.pojo.TokenDto;
import com.getir.company.onlinebookstore.dao.OrderDao;
import com.getir.company.onlinebookstore.dto.OrderDto;

/**
 * 
 * Provides implementation for the orderDao
 * 
 * @author Saravanan Perumal
 *
 */
@Repository
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private CassandraOperations cassandraTemplate;

	@Override
	public OrderDto createOrder(OrderDto orderDetails) {
		return cassandraTemplate.insert(orderDetails);
	}

	@Override
	public OrderDto getOrderById(TokenDto tokenDto, String orderId) {

		return cassandraTemplate.selectOne(Query.query(Criteria.where("userid").is(Long.valueOf(tokenDto.getUserId())))
				.and(Criteria.where("orderid").is(orderId)), OrderDto.class);

	}

	@Override
	public List<OrderDto> getOrderByUserId(Long userId) {

		return cassandraTemplate.select(Query.query(Criteria.where("userid").is(Long.valueOf(userId))), OrderDto.class);
	}

	@Override
	public List<OrderDto> serachOrdersByYear(Integer year, Long userId) {
		return cassandraTemplate.select(
				Query.query(Criteria.where("year").is(year)).and(Criteria.where("userid").is(userId)), OrderDto.class);
	}

	@Override
	public List<OrderDto> searchOrdersByInterval(LocalDateTime startTime, LocalDateTime endTime, Long userId) {
		return cassandraTemplate.select(Query.query(Criteria.where("ordered_date").gte(startTime))
				.and(Criteria.where("ordered_date").lte(endTime)).and(Criteria.where("userid").is(userId))
				.withAllowFiltering(), OrderDto.class);
	}

}
