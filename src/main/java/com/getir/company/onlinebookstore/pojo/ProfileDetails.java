package com.getir.company.onlinebookstore.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Validated
public class ProfileDetails {

	@NotNull
	@Max(value = 10)
	@Min(value = 10)
	public long mobileNumber;

	@NotNull
	public String emailId;

	@NotNull
	public String firstName;

	@NotNull
	public String lastName;
}
