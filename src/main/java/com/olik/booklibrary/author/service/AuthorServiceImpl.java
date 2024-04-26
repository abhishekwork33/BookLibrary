package com.olik.booklibrary.author.service;

import com.olik.booklibrary.author.dto.AuthorDto;
import com.olik.booklibrary.author.model.Author;
import com.olik.booklibrary.author.repository.AuthorRepository;
import com.olik.booklibrary.error.ModelNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


import static com.olik.booklibrary.author.mapper.AuthorMapper.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;


    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> findAllAuthors() {
        return getAuthorDtoList(authorRepository.findAll());
    }

    @Override
    @Transactional
    public AuthorDto createNewAuthor(AuthorDto authorDto) {
        Author newAuthor = toAuthor(authorDto);
        log.info("Author has been saved.");
        return toAuthorDto(authorRepository.save(checksAuthor(newAuthor, authorDto)));
    }

    @Override
    @Transactional
    public AuthorDto updateExistingAuthor(Long id, AuthorDto authorDto) {
        Author author = getById(id);
        log.info("Author has been Updated");
        return toAuthorDto(authorRepository.save(checksAuthor(author, authorDto)));
    }

    @Override
    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(Long authorId) {
        Author author = getById(authorId);
        log.info("Found Author with ID: " + authorId);
        return toAuthorDto(author);
    }

    @Override
    @Transactional
    public void deleteAuthor(Long authorId) {
        authorRepository.deleteById(authorId);
        log.info("Author deleted.");
    }

    private Author checksAuthor(Author author, AuthorDto authorDto) {
        if (authorDto.getName() != null && !authorDto.getName().equals(author.getName())) {
            author.setName(authorDto.getName());
        }
        if (authorDto.getBiography() != null && !authorDto.getBiography().equals(author.getBiography()) && authorDto.getBiography().length()<= 200) {
            author.setBiography(authorDto.getBiography());
        }
        return author;
    }

    private Author getById(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ModelNotFoundException(
                        String.format("Author with id - %d not found!", authorId)
                ));
    }
}
