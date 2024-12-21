package com.trungbeso.dreamshops.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
	private String message;
	private Object data;
}
