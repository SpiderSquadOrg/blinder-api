package com.blinder.api.book.service;

import com.blinder.api.book.model.Book;
import org.springframework.data.domain.Page;

public interface BookService {
    Book addBook(Book book);

    Page<Book> getBooks(Integer page, Integer size);

    Book getBookById(String bookId);

    Book updateBook(String bookId, Book book);

    void deleteBook(String bookId);
}