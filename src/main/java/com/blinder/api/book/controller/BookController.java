package com.blinder.api.book.controller;

import com.blinder.api.book.dto.BookResponseDto;
import com.blinder.api.book.dto.CreateBookRequestDto;
import com.blinder.api.book.dto.UpdateBookRequestDto;
import com.blinder.api.book.mapper.BookMapper;
import com.blinder.api.book.model.Book;
import com.blinder.api.book.service.BookService;
import com.blinder.api.hobby.dto.UpdateHobbyRequestDto;
import com.blinder.api.hobby.mapper.HobbyMapper;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books")
    public ResponseEntity<Page<BookResponseDto>> getAllHobbies(@RequestParam(name = "page", required = false) Integer page,
                                                               @RequestParam(name = "size", required = false) Integer size) {
        return new ResponseEntity<>(BookMapper.INSTANCE.bookToBookResponseDto(bookService.getBooks(page, size)), HttpStatus.OK);
    }


    @GetMapping("/{bookId}")
    @Operation(summary = "Get book by id")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable String bookId) {
        return new ResponseEntity<>(BookMapper.INSTANCE.bookToBookResponseDto(bookService.getBookById(bookId)), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Add new book")
    public ResponseEntity<CreateBookRequestDto> addBook(@RequestBody @Valid CreateBookRequestDto createBookRequestDto) {
        bookService.addBook(BookMapper.INSTANCE.createBookRequestDtoToBook(createBookRequestDto));
        return new ResponseEntity<>(createBookRequestDto, HttpStatus.CREATED);
    }

    @PutMapping("/{bookId}")
    @Operation(summary = "Update book")
    public ResponseEntity<BookResponseDto> updateBook(@PathVariable(name = "bookId") String bookId,
                                                      @RequestBody UpdateBookRequestDto updateBookRequestDto) {
        Book updatedBook = bookService.updateBook(bookId, BookMapper.INSTANCE.updateBookRequestDtoToBook(updateBookRequestDto));
        BookResponseDto bookResponseDto = BookMapper.INSTANCE.bookToBookResponseDto(updatedBook);
        return new ResponseEntity<>(BookMapper.INSTANCE.bookToBookResponseDto(updatedBook), HttpStatus.OK);
    }

    @DeleteMapping("/{bookId}")
    @Operation(summary = "Delete book")
    public ResponseEntity<HttpStatus> deleteBook(@PathVariable(name = "bookId") String bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}