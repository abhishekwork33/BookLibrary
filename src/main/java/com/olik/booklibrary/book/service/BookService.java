package com.olik.booklibrary.book.service;

import com.olik.booklibrary.book.dto.BookDto;

import java.util.List;

public interface BookService {

    List<BookDto> findAllBooks();

    BookDto createBook(Long authorId, BookDto bookDto);

    BookDto updateBook(Long bookId, BookDto bookDto, Long authorId);

    void deleteBook(Long bookId);

    BookDto getBookById(Long bookId);

    List<BookDto> search(String text, int from, int size);

}
