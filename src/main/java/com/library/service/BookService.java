package com.library.service;

import com.library.dto.BookRequestDTO;
import com.library.dto.BookResponseDTO;
import com.library.dto.IssueBookRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    BookResponseDTO create(
            BookRequestDTO dto);

    BookResponseDTO getById(
            Long id);

    Page<BookResponseDTO> getAll(
            int page,
            int size,
            String sortBy,
            String direction);

    BookResponseDTO update(
            Long id,
            BookRequestDTO dto);

    void delete(
            Long id);

    List<BookResponseDTO> search(
            String keyword);

    void issueBook(
            IssueBookRequest request);

    void returnBook(
            Long issueId);
}