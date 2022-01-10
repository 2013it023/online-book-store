package com.getir.company.onlinebookstore.dto;

import java.io.Serializable;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("book_dto")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKeyColumn(name = "userid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
	private Long userId;

	@PrimaryKeyColumn(name = "id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
	private String id;

	@Column
	private String name;

	@Column
	private String description;

	@Column
	private long price;

	@Column("author_name")
	private String authorName;

	@Column
	private long quantity;

}
