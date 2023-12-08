package com.blinder.api.book.service.impl;

import com.blinder.api.book.model.Book;
import com.blinder.api.book.repository.BookRepository;
import com.blinder.api.book.service.BookService;
import com.blinder.api.hobby.model.Hobby;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import static com.blinder.api.util.MappingUtils.getNullPropertyNames;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Page<Book> getBooks(Integer page, Integer size) {
        boolean isPageable = Objects.nonNull(page) && Objects.nonNull(size);

        if (!isPageable) {
            page = 0;
            size = Integer.MAX_VALUE;
        }
        return this.bookRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Book getBookById(String bookId) {
        return this.bookRepository.findById(bookId).orElseThrow();
    }

    @Override
    public Book updateBook(String bookId, Book book) {
        Book bookToUpdate = this.bookRepository.findById(bookId).orElseThrow();

        Set<String> nullPropertyNames = getNullPropertyNames(book);

        BeanUtils.copyProperties(book, bookToUpdate, nullPropertyNames.toArray(new String[0]));

        this.bookRepository.save(bookToUpdate);
        return bookToUpdate;
    }

    @Override
    public void deleteBook(String bookId) {
        this.bookRepository.deleteById(bookId);
    }
}
