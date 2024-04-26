package com.olik.booklibrary.rental.controller;

import com.olik.booklibrary.rental.dto.RentalDto;
import com.olik.booklibrary.rental.service.RentalService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor

public class RentalController {
    private final RentalService rentalService;

    @PostMapping
    public RentalDto createRental(@RequestBody RentalDto rentalDto, @RequestParam String renterName) {
        return rentalService.createRental(rentalDto, renterName);
    }

    @PatchMapping("/{rentalId}")
    public RentalDto updateRental(@PathVariable Long rentalId, @RequestBody RentalDto rentalDto, @RequestParam String renterName) {
        return rentalService.updateRental(rentalId, rentalDto, renterName);
    }

    @DeleteMapping("/{rentalId}")
    public void deleteRental(@PathVariable Long rentalId) {
        rentalService.deleteRental(rentalId);
    }

    @PostMapping("/{bookId}/rent")
    public void rentBook(@PathVariable Long bookId, @RequestParam String renterName) {
        rentalService.rentBook(bookId, renterName);
    }

    @PostMapping("/{bookId}/return")
    public void returnBook(@PathVariable Long bookId) {
        rentalService.returnBook(bookId);
    }

    @GetMapping("/overdue")
    public List<RentalDto> checkOverdueRentals() {
        return  rentalService.checkOverdueRentals();
    }

    @GetMapping("/{rentalId}")
    public RentalDto getRentalById(@PathVariable Long rentalId) {
        return rentalService.getRentalById(rentalId);
    }
}