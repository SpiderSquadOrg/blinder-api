package com.blinder.api.book.mapper;

import com.blinder.api.book.dto.BookResponseDto;
import com.blinder.api.book.dto.CreateBookRequestDto;
import com.blinder.api.book.dto.UpdateBookRequestDto;
import com.blinder.api.book.model.Book;
import com.blinder.api.hobby.dto.HobbyResponseDto;
import com.blinder.api.hobby.model.Hobby;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

@Mapper(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface BookMapper {
    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    List<BookResponseDto> bookToBookResponseDto(List<Book> books);

    BookResponseDto bookToBookResponseDto(Book book);

    Book bookResponseDtoToBook(BookResponseDto bookResponseDto);


    Book createBookRequestDtoToBook(CreateBookRequestDto createBookRequestDto);


    Book updateBookRequestDtoToBook(UpdateBookRequestDto updateBookRequestDto);

    default Page<BookResponseDto> bookToBookResponseDto(Page<Book> bookPage) {
        List<BookResponseDto> responseDtoList = bookToBookResponseDto(bookPage.getContent());
        return new PageImpl<>(responseDtoList, bookPage.getPageable(), bookPage.getTotalElements());
    }
}
