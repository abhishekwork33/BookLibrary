package com.olik.booklibrary.book.mapper;

import com.olik.booklibrary.book.dto.BookDto;
import com.olik.booklibrary.book.model.Book;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class BookMapper {

    public static BookDto toBookDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .available(book.getAvailable())
                .build();
    }

    public static Book toBook(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.getId())
                .title(bookDto.getTitle())
                .available(bookDto.getAvailable())
                .build();
    }

    public static List<BookDto> getBookListDto(List<Book> bookList) {
        return bookList.stream().map(BookMapper::toBookDto).collect(Collectors.toList());
    }
}
