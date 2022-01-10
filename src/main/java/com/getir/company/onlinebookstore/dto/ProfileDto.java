package com.getir.company.onlinebookstore.dto;

import java.io.Serializable;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table("profile_dto")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProfileDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@PrimaryKey
	private long mobileNumber;

	@Column("email_id")
	private String emailId;

	@Column("first_name")
	private String firstName;

	@Column("last_name")
	private String lastName;

	@Column("created_at")
	private String createdAt;

}
