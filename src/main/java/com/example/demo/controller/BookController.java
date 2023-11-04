package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class BookController {
	@Autowired
	private BookService bookService;

	@GetMapping("/book")
	public ResponseEntity<List<Book>> getAllBooks() {
		log.info("Retrieving all books.");
		List<Book> books = bookService.getAllBooks();
		return ResponseEntity.ok(books);
	}

	@GetMapping("/book/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable Long id) {
		log.info("Retrieving book with id: {}", id);
		Book book = bookService.getBookById(id);
		return ResponseEntity.ok(book);
	}

	@PostMapping("/book")
	public ResponseEntity<Book> addBook(@Valid @RequestBody Book book) {
		log.info("Adding book. \n{}", book);
		Book bookCreated = bookService.addBook(book);
		return ResponseEntity.created(URI.create("/book/" + bookCreated.getId())).body(bookCreated);
	}

	@PutMapping("/book/{id}")
	public ResponseEntity<Book> updateBookById(@PathVariable Long id, @Valid @RequestBody Book book) {
		log.info("Updating book with id: {}", id);
		log.info("New book: \n{}", book);
		Pair<Book, Boolean> upsertedBook = bookService.updateBookById(id, book);

		if (upsertedBook.getSecond()) {
			return ResponseEntity.ok(upsertedBook.getFirst());
		}
		return ResponseEntity.created(URI.create("/book/" + upsertedBook.getFirst().getId()))
				.body(upsertedBook.getFirst());
	}

	@DeleteMapping("/book/{id}")
	public ResponseEntity<Long> deleteBookById(@PathVariable Long id) {
		log.info("Deleting book with id: {}", id);
		bookService.deleteBookById(id);
		return ResponseEntity.ok(id);
	}
}
