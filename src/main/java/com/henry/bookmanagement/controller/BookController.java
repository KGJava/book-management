package com.henry.bookmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.henry.bookmanagement.exception.ResourceNotFoundException;
import com.henry.bookmanagement.model.Book;
import com.henry.bookmanagement.repository.BookRepository;

/**
 * Created by swapnil .
 */
@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	BookRepository bookRepository;

	@GetMapping("/")
	public List<Book> getAllbooks() {
		return bookRepository.findAll();
	}

	@PostMapping(value = "/")
	public Book createbook(@Valid @RequestBody Book book) {
		return bookRepository.save(book);
	}

	@GetMapping("/{id}")
	public Book getbookById(@PathVariable(value = "id") Long bookId) {
		return bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("book", "id", bookId));
	}

	@PutMapping("/{id}")
	public Book updatebook(@PathVariable(value = "id") Long bookId, @Valid @RequestBody Book bookDetails) {

		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("book", "id", bookId));

		book.setTitle(bookDetails.getTitle());
		book.setDescription(bookDetails.getDescription());
		book.setPrice(bookDetails.getPrice());
		book.setThumbnailUrl(bookDetails.getThumbnailUrl());

		Book updatedbook = bookRepository.save(book);
		return updatedbook;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletebook(@PathVariable(value = "id") Long bookId) {
		Book book = bookRepository.findById(bookId)
				.orElseThrow(() -> new ResourceNotFoundException("book", "id", bookId));

		bookRepository.delete(book);

		return ResponseEntity.ok().build();
	}
}
