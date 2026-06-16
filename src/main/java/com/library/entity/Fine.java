package com.library.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "fines")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private IssueBook issueBook;

    private Double amount;

    private Boolean paid;

    private LocalDate paymentDate;
}