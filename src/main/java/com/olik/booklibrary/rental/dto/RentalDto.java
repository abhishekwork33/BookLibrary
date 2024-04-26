package com.olik.booklibrary.rental.dto;

import com.olik.booklibrary.book.model.Book;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class RentalDto {

    @Future
    @NotNull
    private LocalDateTime rentalDate;
    @Future
    @NotNull
    private LocalDateTime returnDate;
    @NotBlank
    private String renterName;
    private Book book;

    @AssertTrue
    private boolean isEndAfterStart() {
        return rentalDate == null || returnDate == null || returnDate.isAfter(rentalDate);
    }
}
