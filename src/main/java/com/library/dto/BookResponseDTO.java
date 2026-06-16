package com.library.dto;

import lombok.Data;

@Data
public class BookResponseDTO {

    private Long id;
    private String isbn;
    private String title;
    private String author;

    private Long categoryId;
    private String categoryName;

    private String publisher;
    private Integer totalCopies;
    private Integer availableCopies;

    private String imageName;
}