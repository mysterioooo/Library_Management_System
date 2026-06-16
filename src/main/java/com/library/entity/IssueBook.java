package com.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "issued_books")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Book book;

    private LocalDate issueDate;

    private LocalDate dueDate;

    private LocalDate returnDate;

    private String status;
}