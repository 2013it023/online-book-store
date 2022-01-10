package com.getir.company.onlinebookstore.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.getir.company.onlinebookstore.pojo.UpdateDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBookDetailsRequest {

	@NotEmpty(message = "Missing Book Details in the request payload to update.")
	List<UpdateDetails> bookDetails;

}
