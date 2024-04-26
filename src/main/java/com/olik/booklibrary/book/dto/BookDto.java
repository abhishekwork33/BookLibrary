package com.olik.booklibrary.book.dto;

import com.olik.booklibrary.rental.dto.RentalDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Long id;
    @NotBlank
    private String title;
    @NotNull
    private Boolean available;
    @NotNull
    private Date publicationYear;
}
