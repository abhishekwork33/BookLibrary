package com.olik.booklibrary.book.repository;

import com.olik.booklibrary.book.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {

    @Query("SELECT b FROM Book b WHERE b.available = true AND LOWER(b.title) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Page<Book> searchByTitleContainingIgnoreCase(String partialTitle, Pageable page);

    Page<Book> findAllByAuthorIdOrderById(Long authorId, Pageable page);
}
