package com.example.demo.exception;

public class BookToBeDeletedNotFoundException extends RuntimeException {
	public BookToBeDeletedNotFoundException(String message) {
		super(message);
	}
}
