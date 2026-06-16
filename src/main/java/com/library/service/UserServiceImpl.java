package com.library.service;

import com.library.dto.DashboardDTO;
import com.library.dto.ReservationRequest;
import com.library.dto.UserRequest;
import com.library.dto.UserResponseDTO;
import com.library.entity.*;
import com.library.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final IssueBookRepository issueRepository;
    private final FineRepository fineRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDTO> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();

                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());

                    return dto;
                })
                .toList();
    }

    @Override
    public UserResponseDTO getUser(Long id) {

        return userRepository.findById(id)
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();

                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    dto.setRole(user.getRole());

                    return dto;
                })
                .orElseThrow();
    }

    @Override
    public void deleteUser(Long id) {

        userRepository.deleteById(id);
    }

    @Override
    public void reserveBook(ReservationRequest request) {

        System.out.println("UserId = " + request.getUserId());
        System.out.println("BookId = " + request.getBookId());

        Book book = bookRepository.findById(
                        request.getBookId())
                .orElseThrow(() ->
                        new RuntimeException("Book not found"));

        System.out.println(
                "Available Copies = " +
                        book.getAvailableCopies());

        User user = userRepository.findById(
                        request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if(book.getAvailableCopies() > 0){
            throw new RuntimeException(
                    "Book available. No reservation needed");
        }

        Reservation reservation =
                Reservation.builder()
                        .book(book)
                        .user(user)
                        .reservationDate(LocalDate.now())
                        .build();

        reservationRepository.save(reservation);
    }

    @Override
    public DashboardDTO dashboard() {

        return DashboardDTO.builder()
                .totalBooks(bookRepository.count())
                .totalUsers(userRepository.count())
                .totalIssuedBooks(issueRepository.count())
                .totalFines(fineRepository.count())
                .build();
    }

    @Override
    public User updateUser(
            Long id,
            UserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(Role.valueOf(request.getRole()));

        if(request.getPassword() != null &&
                !request.getPassword().isBlank()) {

            user.setPassword(
                    passwordEncoder.encode(
                            request.getPassword()));
        }

        return userRepository.save(user);
    }
}