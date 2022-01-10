package com.getir.company.onlinebookstore.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("order_dto")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class OrderDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "userid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private Long userId;

	@PrimaryKeyColumn(name = "orderid", ordinal = 0, type = PrimaryKeyType.CLUSTERED)
	private String orderId;

	@Column("ordered_date")
	@Indexed
	private LocalDateTime orderedDate;

	@Column("payment_type")
	private Integer paymentType;

	@Column("total_amount")
	private Float totalAmount;

	@Column("book_details")
	private List<BookDetails> bookDetails;

	@Column("payment_information")
	private PaymentInformation paymentInformation;

	@Column("year")
	@Indexed
	private Integer year;

	@Column("month")
	private String month;

}
