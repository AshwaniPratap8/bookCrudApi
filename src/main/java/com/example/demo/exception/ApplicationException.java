package com.example.demo.exception;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApplicationException {
	private HttpStatus status;
	private Long errorCode;
	private String errorMessage;
	private List<String> errors;

	public ApplicationException(HttpStatus status, Long errorCode, String errorMessage, List<String> errors) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errors = errors;
	}

	public ApplicationException(HttpStatus status, Long errorCode, String errorMessage, String error) {
		this.status = status;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		errors = Arrays.asList(error);
	}
}
