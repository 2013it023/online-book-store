package com.getir.company.onlinebookstore.core.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorInfo {

	private String timeStamp;

	private String e2eRequestId;

	private String errorCode;

	private String errorName;

	private String errorMessage;

}
