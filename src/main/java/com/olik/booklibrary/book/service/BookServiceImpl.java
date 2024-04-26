package com.olik.booklibrary.book.service;

import com.olik.booklibrary.author.model.Author;
import com.olik.booklibrary.author.repository.AuthorRepository;
import com.olik.booklibrary.book.dto.BookDto;
import com.olik.booklibrary.book.mapper.BookMapper;
import com.olik.booklibrary.book.model.Book;
import com.olik.booklibrary.book.repository.BookRepository;
import com.olik.booklibrary.error.BookAlreadyExistsException;
import com.olik.booklibrary.error.ModelNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.olik.booklibrary.book.mapper.BookMapper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAllBooks() {
        log.info("Found list of all books");
        return getBookListDto(bookRepository.findAll());
    }

    @Override
    @Transactional
    public BookDto createBook(Long authorId, BookDto bookDto) {
        Author savedAuthor = getAuthorById(authorId);
        Book book = toBook(bookDto);
        book.setAuthor(savedAuthor);
        log.info("Book has been saved.");
        return toBookDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookDto updateBook(Long bookId, BookDto bookDto, Long authorId) {
        Book book = toBook(getBookById(bookId));
        if (!authorId.equals(book.getAuthor().getId()) && !book.getTitle().equals(bookDto.getTitle()) ) {
            throw new BookAlreadyExistsException("Author Id Incorrect.");
        }
        Book updatedBook = bookRepository.save(checksBook(book, bookDto));
        log.info("Book with id " + updatedBook.getId() + " has been updated");
        return toBookDto(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long bookId) {
        log.info("Book has been deleted");
        bookRepository.deleteById(bookId);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto getBookById(Long bookId) {
        return toBookDto(bookRepository.findById(bookId).orElseThrow(() ->
                new ModelNotFoundException("Incorrect Book ID.")));
    }


    @Override
    public List<BookDto> search(String text, int from, int size) {
        if (text.isBlank()) {
            log.info("The command is empty");
            return new ArrayList<>();
        }
        String query = text.toLowerCase();
        Pageable page = PageRequest.of(from / size, size);
        Page<Book> books = bookRepository.searchByTitleContainingIgnoreCase(query, page);
        if (books.isEmpty()) {
            log.info("With the provided title " + query + " no books has been found.");
            return new ArrayList<>();
        }
        log.info("The list of books with the provided title  " + query + " has been found.");
        return books.stream()
                .map(BookMapper::toBookDto)
                .collect(Collectors.toList());
    }

    private Author getAuthorById(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(() ->
                new ModelNotFoundException("Incorrect Author Id."));
    }


    private Book checksBook(Book book, BookDto bookDto) {
        if (bookDto.getTitle() != null && !bookDto.getTitle().equals(book.getTitle())) {
            book.setTitle(bookDto.getTitle());
        }
        if (bookDto.getAvailable() != null && bookDto.getAvailable() != book.getAvailable()) {
            book.setAvailable(bookDto.getAvailable());
        }
        return book;
    }


}
