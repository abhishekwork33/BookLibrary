package com.olik.booklibrary.book.controller;

import com.olik.booklibrary.book.dto.BookDto;
import com.olik.booklibrary.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("/{bookId}")
    public BookDto getBookById(@PathVariable Long bookId) {
        return bookService.getBookById(bookId);
    }

    @PostMapping
    public BookDto createBook(@RequestParam Long authorId, @RequestBody BookDto bookDto) {
        return bookService.createBook(authorId, bookDto);
    }

    @PatchMapping("/{bookId}")
    public BookDto updateBook(@PathVariable Long bookId, @RequestParam Long authorId, @RequestBody BookDto bookDto) {
        return bookService.updateBook(bookId, bookDto, authorId);
    }

    @DeleteMapping("/{bookId}")
    public void deleteBook(@PathVariable Long bookId) {
         bookService.deleteBook(bookId);
    }

    @GetMapping("/search")
    public List<BookDto> searchBooks(@RequestParam String text, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "20") int size) {
        return bookService.search(text, from, size);
    }
}
