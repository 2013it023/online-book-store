package com.getir.company.onlinebookstore.response;

import java.util.List;

import com.getir.company.onlinebookstore.pojo.BookDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddBookResponse {

	List<BookDetails> bookDetails;

}
