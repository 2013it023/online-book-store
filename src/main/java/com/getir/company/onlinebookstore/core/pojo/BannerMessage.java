package com.getir.company.onlinebookstore.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BannerMessage {

	public String code;

	public String message;

	public String type;

}
