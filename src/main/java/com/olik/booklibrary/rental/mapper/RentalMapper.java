package com.olik.booklibrary.rental.mapper;

import com.olik.booklibrary.book.dto.BookDto;
import com.olik.booklibrary.book.mapper.BookMapper;
import com.olik.booklibrary.book.model.Book;
import com.olik.booklibrary.rental.dto.RentalDto;
import com.olik.booklibrary.rental.model.Rental;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class RentalMapper {
    public static RentalDto toRentalDto(Rental rental) {
        return RentalDto.builder()
                .rentalDate(rental.getRentalDate())
                .returnDate(rental.getReturnDate())
                .renterName(rental.getRenterName())
                .book(rental.getBook())
                .build();
    }

    public static Rental toRental(RentalDto rentalDto, Book book) {
        return Rental.builder()
                .rentalDate(rentalDto.getRentalDate())
                .returnDate(rentalDto.getReturnDate())
                .renterName(rentalDto.getRenterName())
                .book(book)
                .build();
    }

    public static List<RentalDto> getRentalDtoList(List<Rental> rentalList) {
        return rentalList.stream().map(RentalMapper::toRentalDto).collect(Collectors.toList());
    }
}
