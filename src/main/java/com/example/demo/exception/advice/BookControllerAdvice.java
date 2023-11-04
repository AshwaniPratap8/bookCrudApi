package com.example.demo.exception.advice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.exception.ApplicationException;
import com.example.demo.exception.BookToBeDeletedNotFoundException;
import com.example.demo.exception.NoBookFoundException;
import com.example.demo.exception.NoSuchBookException;

@ControllerAdvice
public class BookControllerAdvice extends ResponseEntityExceptionHandler {
	@ExceptionHandler(BookToBeDeletedNotFoundException.class)
	public ResponseEntity<ApplicationException> handleBookToBeDeletedNotFoundException(
			BookToBeDeletedNotFoundException ex) {
		ApplicationException exception = new ApplicationException(HttpStatus.BAD_REQUEST, 10001L, "Book Not Exists",
				ex.getMessage());
		return new ResponseEntity<>(exception, exception.getStatus());
	}

	@ExceptionHandler(NoBookFoundException.class)
	public ResponseEntity<ApplicationException> handleNoBookFoundException(NoBookFoundException ex) {
		ApplicationException exception = new ApplicationException(HttpStatus.NO_CONTENT, 10002L, "No Book Found",
				ex.getMessage());
		return new ResponseEntity<>(exception, exception.getStatus());
	}

	@ExceptionHandler(NoSuchBookException.class)
	public ResponseEntity<ApplicationException> handleNoSuchBookException(NoSuchBookException ex) {
		ApplicationException exception = new ApplicationException(HttpStatus.NOT_FOUND, 10003L, "No Such Book",
				ex.getMessage());
		return new ResponseEntity<>(exception, exception.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});

		List<String> errorMessages = errors.entrySet().stream().map(entry -> entry.getKey() + ": " + entry.getValue())
				.collect(Collectors.toList());

		ApplicationException exception = new ApplicationException(HttpStatus.BAD_REQUEST, 10004L, "Invalid Arguments",
				errorMessages);

		return new ResponseEntity<>(exception, exception.getStatus());
	}
}
