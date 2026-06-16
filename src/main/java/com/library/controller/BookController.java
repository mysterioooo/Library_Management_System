package com.library.controller;

import com.library.dto.BookRequestDTO;
import com.library.dto.BookResponseDTO;
import com.library.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService service;

    @PreAuthorize(
            "hasAnyRole('ADMIN','LIBRARIAN')")
    @PostMapping
    public ResponseEntity<BookResponseDTO> create(
            @Valid
            @RequestBody
            BookRequestDTO dto) {

        return ResponseEntity.ok(
                service.create(dto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDTO>>
    search(
            @RequestParam String keyword) {

        return ResponseEntity.ok(
                service.search(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO>
    getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<BookResponseDTO>>
    getAll(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "5")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "asc")
            String direction) {

        return ResponseEntity.ok(
                service.getAll(
                        page,
                        size,
                        sortBy,
                        direction));
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','LIBRARIAN')")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO>
    update(
            @PathVariable Long id,
            @Valid
            @RequestBody
            BookRequestDTO dto) {

        return ResponseEntity.ok(
                service.update(id, dto));
    }

    @PreAuthorize(
            "hasAnyRole('ADMIN','LIBRARIAN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}