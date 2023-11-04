package com.example.demo.exception;

public class NoBookFoundException extends RuntimeException {
	public NoBookFoundException(String message) {
		super(message);
	}
}
