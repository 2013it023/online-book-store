package com.getir.company.onlinebookstore.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDetails {

	public String name;

	public long price;

	public String description;

	public long quantity;

	public String authorName;

	public String id;

}
