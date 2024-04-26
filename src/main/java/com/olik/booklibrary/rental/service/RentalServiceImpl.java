package com.olik.booklibrary.rental.service;

import com.olik.booklibrary.book.model.Book;
import com.olik.booklibrary.book.repository.BookRepository;
import com.olik.booklibrary.error.BookAlreadyRentedException;
import com.olik.booklibrary.error.ModelNotFoundException;
import com.olik.booklibrary.error.RentalNotFoundException;
import com.olik.booklibrary.rental.dto.RentalDto;
import com.olik.booklibrary.rental.mapper.RentalMapper;
import com.olik.booklibrary.rental.model.Rental;
import com.olik.booklibrary.rental.repository.RentalRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.olik.booklibrary.rental.mapper.RentalMapper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService {

    private final RentalRepository rentalRepository;
    private final BookRepository bookRepository;


    @Override
    @Transactional
    public RentalDto createRental(RentalDto rentalDto, String renterName) {
        Book book = rentalDto.getBook();

        if (book == null || book.getId() == null) {
            throw new IllegalArgumentException("Book ID must be provided for rental.");
        }
        Book rentedBook = getBookById(book.getId());
        if (!rentedBook.getAvailable()) {
            throw new BookAlreadyRentedException("Book with ID " + rentedBook.getId() + " is already rented.");
        }

        rentedBook.setAvailable(false);
        bookRepository.save(rentedBook);
        Rental rental = toRental(rentalDto,rentedBook);
        log.info("A new rental has been created ");
        return RentalMapper.toRentalDto(rentalRepository.save(rental));
    }

    @Override
    @Transactional
    public RentalDto updateRental(Long rentalId, RentalDto rentalDto, String renterName) {
        Rental existingRental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException("Rental with ID " + rentalId + " not found."));

        if (!existingRental.getRenterName().equals(renterName)) {
            throw new IllegalArgumentException("Rental with ID " + rentalId + " does not belong to " + renterName);
        }

        Book book = rentalDto.getBook();
        if (book == null || book.getId() == null) {
            throw new IllegalArgumentException("Book ID must be provided for rental.");
        }
        Book rentedBook = getBookById(book.getId());
        if (!rentedBook.getAvailable()) {
            throw new BookAlreadyRentedException("Book with ID " + rentedBook.getId() + " is already rented.");
        }

        rentedBook.setAvailable(false);
        bookRepository.save(rentedBook);

        existingRental.setRentalDate(rentalDto.getRentalDate());
        existingRental.setReturnDate(rentalDto.getReturnDate());
        existingRental.setBook(rentedBook);

        return RentalMapper.toRentalDto(rentalRepository.save(existingRental));
    }

    @Override
    @Transactional
    public void deleteRental(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException("Rental with ID " + rentalId + " not found."));
        Book book = rental.getBook();
        book.setAvailable(true);
        bookRepository.save(book);
        rentalRepository.delete(rental);
    }
    @Override
    @Transactional
    public void rentBook(Long bookId, String renterName) {
        Book book = getBookById(bookId);
        if (!book.getAvailable()) {
            throw new BookAlreadyRentedException("Book with ID " + bookId + " is already rented.");
        }
        book.setAvailable(false);
        bookRepository.save(book);
        Rental rental = Rental.builder()
                .rentalDate(LocalDateTime.now())
                .renterName(renterName)
                .book(book)
                .build();
        rentalRepository.save(rental);
        log.info("Book with ID " + bookId + " has been rented by " + renterName);
    }

    @Override
    @Transactional
    public void returnBook(Long bookId) {
        Book book = getBookById(bookId);
        book.setAvailable(true);
        bookRepository.save(book);
        Rental rental = rentalRepository.findTopByBookIdOrderByRentalDateDesc(bookId)
                .orElseThrow(() -> new RentalNotFoundException("No rental found for book with ID " + bookId));
        rental.setReturnDate(LocalDateTime.now());
        rentalRepository.save(rental);
        log.info("Book with ID " + bookId + " has been returned.");
    }

    @Override
    public List<RentalDto> checkOverdueRentals() {
        LocalDateTime now = LocalDateTime.now();
        List<Rental> overdueRentals = rentalRepository.findAllByReturnDateIsNullAndRentalDateBefore(now.minusDays(14));
        log.info("Found " + overdueRentals.size() + " overdue rentals.");
        return getRentalDtoList(overdueRentals);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RentalDto> getAllRentals() {
        log.info("Found all rental list");
        return getRentalDtoList(rentalRepository.findAll());
    }

    @Override
    public RentalDto getRentalById(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId)
                .orElseThrow(() -> new RentalNotFoundException("Rental with ID " + rentalId + " not found."));
        return toRentalDto(rental);
    }

    private Book getBookById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() ->
                new ModelNotFoundException("Book with ID " + bookId + " not found."));
    }
}

