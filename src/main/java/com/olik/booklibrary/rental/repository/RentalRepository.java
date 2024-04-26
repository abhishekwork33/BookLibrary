package com.olik.booklibrary.rental.repository;

import com.olik.booklibrary.rental.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental,Long> {

    Optional<Rental> findTopByBookIdOrderByRentalDateDesc(Long bookId);

    List<Rental> findAllByReturnDateIsNullAndRentalDateBefore(LocalDateTime date);
}
