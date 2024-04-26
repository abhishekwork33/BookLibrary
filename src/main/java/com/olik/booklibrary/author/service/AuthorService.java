package com.olik.booklibrary.author.service;

import com.olik.booklibrary.author.dto.AuthorDto;

import java.util.List;

public interface AuthorService {

    List<AuthorDto> findAllAuthors();

    AuthorDto createNewAuthor(AuthorDto authorDto);

    AuthorDto updateExistingAuthor(Long id, AuthorDto authorDto);

    AuthorDto getAuthorById(Long authorId);

    void deleteAuthor(Long authorId);
}
