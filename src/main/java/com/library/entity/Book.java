package com.library.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "books")
@RequiredArgsConstructor // Generates constructor for final/NonNull fields
@AllArgsConstructor // Required by @Builder
@Data
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;

    private String title;

    private String author;

    private String publisher;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer totalCopies;

    private Integer availableCopies;

    private String imageName;
}