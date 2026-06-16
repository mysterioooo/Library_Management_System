package com.library.service;

import com.library.dto.BookRequestDTO;
import com.library.dto.BookResponseDTO;
import com.library.dto.IssueBookRequest;
import com.library.entity.*;
import com.library.exception.ResourceNotFoundException;
import com.library.mapper.BookMapper;
import com.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository repository;
    private final BookMapper mapper;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final IssueBookRepository issueRepository;
    private final FineRepository fineRepository;

    @Override
    public BookResponseDTO create(BookRequestDTO dto) {

        System.out.println("IMAGE FROM DTO = " + dto.getImageName());

        if (repository.existsByIsbn(dto.getIsbn())) {
            throw new RuntimeException("ISBN already exists");
        }

        Book book = mapper.toEntity(dto);

        // IMPORTANT
        book.setImageName(dto.getImageName());

        if (dto.getCategoryId() != null) {

            Category category =
                    categoryRepository.findById(dto.getCategoryId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("Category not found"));

            book.setCategory(category);
        }

        book.setAvailableCopies(dto.getTotalCopies());

        Book savedBook = repository.save(book);

        System.out.println("IMAGE SAVED = " + savedBook.getImageName());

        return mapper.toDto(savedBook);
    }

    @Override
    public BookResponseDTO getById(Long id) {

        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        return mapper.toDto(book);
    }

    @Override
    public Page<BookResponseDTO> getAll(int page, int size, String sortBy, String direction) {

        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public BookResponseDTO update(Long id, BookRequestDTO dto) {

        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (dto.getTotalCopies() == null || dto.getTotalCopies() <= 0) {
            throw new RuntimeException("Total copies must be greater than 0");
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        int difference = dto.getTotalCopies() - book.getTotalCopies();

        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setPublisher(dto.getPublisher());
        book.setImageName(dto.getImageName());
        book.setIsbn(dto.getIsbn());
        book.setCategory(category);
        book.setTotalCopies(dto.getTotalCopies());
        book.setAvailableCopies(Math.max(0, book.getAvailableCopies() + difference));

        return mapper.toDto(repository.save(book));
    }

    @Override
    public void delete(Long id) {

        Book book = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        repository.delete(book);
    }

    @Override
    public List<BookResponseDTO> search(String keyword) {

        return repository.searchBooks(keyword)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    @Override
    public void issueBook(IssueBookRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Book book = repository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("Book not available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        repository.save(book);

        IssueBook issue = IssueBook.builder()
                .user(user)
                .book(book)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(7))
                .status("ISSUED")
                .build();

        issueRepository.save(issue);
    }

    @Override
    public void returnBook(Long issueId) {

        IssueBook issue = issueRepository.findById(issueId)
                .orElseThrow();

        LocalDate today = LocalDate.now();

        if (today.isAfter(issue.getDueDate())) {

            Fine fine = fineRepository.findByIssueBook_Id(issueId).orElse(null);

            if (fine == null) {
                long daysLate = ChronoUnit.DAYS.between(issue.getDueDate(), today);

                fine = Fine.builder()
                        .issueBook(issue)
                        .amount(daysLate * 10.0)
                        .paid(false)
                        .build();

                fineRepository.save(fine);
            }

            if (!fine.getPaid()) {
                throw new RuntimeException("Fine payment required before returning book");
            }
        }

        issue.setReturnDate(today);

        Book book = issue.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        repository.save(book);
        issueRepository.save(issue);
    }
}