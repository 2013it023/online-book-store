package com.getir.company.onlinebookstore.core.pojo;

import lombok.Data;

@Data
public class ServiceResponse<T> {

	private T data;

	private ErrorInfo errorInfo;

	private BannerMessage message;

}
