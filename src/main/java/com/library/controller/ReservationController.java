package com.library.controller;

import com.library.dto.ReservationRequest;
import com.library.entity.Reservation;
import com.library.repository.ReservationRepository;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final UserService service;
    private final ReservationRepository reservationRepository;

    @PostMapping
    public ResponseEntity<String> reserveBook(
            @RequestBody ReservationRequest request){

        service.reserveBook(request);

        return ResponseEntity.ok(
                "Book Reserved");
    }

    @GetMapping
    public ResponseEntity<List<Reservation>>
    getAllReservations(){

        return ResponseEntity.ok(
                reservationRepository.findAll());
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getUserReservations(
            @PathVariable Long userId){

        return reservationRepository
                .findByUser_Id(userId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(
            @PathVariable Long id){

        reservationRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}