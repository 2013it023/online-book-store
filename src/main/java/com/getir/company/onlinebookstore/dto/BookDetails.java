package com.getir.company.onlinebookstore.dto;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@UserDefinedType("book_details")

public class BookDetails implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column("book_id")
	private String bookId;

	@Column("name")
	private String name;
	
	@Column("quantity")
	public long quantity;
}
