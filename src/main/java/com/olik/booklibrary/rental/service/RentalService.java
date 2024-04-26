package com.olik.booklibrary.rental.service;

import com.olik.booklibrary.rental.dto.RentalDto;

import java.util.List;

public interface RentalService {

    void rentBook(Long bookId, String renterName);

    void returnBook(Long bookId);

    List<RentalDto> checkOverdueRentals();

    List<RentalDto> getAllRentals();

    RentalDto getRentalById(Long rentalId);

    RentalDto createRental(RentalDto rentalDto, String renterName);

    RentalDto updateRental(Long rentalId, RentalDto rentalDto, String renterName);

    void deleteRental(Long rentalId);
}

