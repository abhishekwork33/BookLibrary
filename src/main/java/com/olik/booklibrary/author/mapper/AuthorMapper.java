package com.olik.booklibrary.author.mapper;

import com.olik.booklibrary.author.dto.AuthorDto;
import com.olik.booklibrary.author.model.Author;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class AuthorMapper {

    public static Author toAuthor (AuthorDto authorDto){
        return Author.builder()
                .id(authorDto.getId())
                .name(authorDto.getName())
                .biography(authorDto.getBiography())
                .build();
    }

    public static AuthorDto toAuthorDto (Author author){
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .biography(author.getBiography())
                .build();

    }

    public static List<AuthorDto> getAuthorDtoList(List<Author> authorList){
        return authorList.stream().map(AuthorMapper::toAuthorDto).collect(Collectors.toList());
    }

}
