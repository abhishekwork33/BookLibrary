package com.olik.booklibrary.author.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    @Size(min = 10, max = 200)
    private String biography;
}
