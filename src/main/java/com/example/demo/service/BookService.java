package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import com.example.demo.exception.BookToBeDeletedNotFoundException;
import com.example.demo.exception.NoBookFoundException;
import com.example.demo.exception.NoSuchBookException;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

@Service
public class BookService {
	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAllBooks() {
		List<Book> books = bookRepository.findAll();

		if (books.isEmpty()) {
			throw new NoBookFoundException("No book is available in database");
		}

		return books;
	}

	public Book getBookById(Long id) {
		Optional<Book> book = bookRepository.findById(id);

		if (book.isEmpty()) {
			throw new NoSuchBookException(String.format("No book with id %s is available in database", id));
		}

		return book.get();
	}

	public Book addBook(Book book) {
		return bookRepository.save(book);
	}

	public Pair<Book, Boolean> updateBookById(Long id, Book book) {
		Optional<Book> foundBook = bookRepository.findById(id);

		if (foundBook.isPresent()) {
			Book updatedBook = foundBook.get();
			updatedBook.setTitle(book.getTitle());
			updatedBook.setAuthor(book.getAuthor());
			return Pair.of(bookRepository.save(updatedBook), true);
		}

		return Pair.of(bookRepository.save(book), false);
	}

	public void deleteBookById(Long id) {
		try {
			bookRepository.deleteById(id);
		} catch (EmptyResultDataAccessException | IllegalArgumentException e) {
			throw new BookToBeDeletedNotFoundException(String.format("Book with id %s not found", id));
		}
	}
}
