package com.getir.company.onlinebookstore.request;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import com.getir.company.onlinebookstore.pojo.BookDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBookRequest {

	@NotEmpty(message = "Missing Book Details in the request payload.")
	List<BookDetails> bookDetails;

}
