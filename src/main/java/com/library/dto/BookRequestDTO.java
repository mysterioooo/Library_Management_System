package com.library.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class BookRequestDTO {

    @NotBlank
    private String isbn;

    @NotBlank
    private String title;

    @NotBlank
    private String author;

    private String publisher;

    private Long categoryId;

    @Min(1)
    private Integer totalCopies;

    private String imageName;
}