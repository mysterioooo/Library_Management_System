package com.library.controller;

import com.library.dto.UserRequest;
import com.library.dto.UserResponseDTO;
import com.library.entity.User;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getUsers() {

        return ResponseEntity.ok(
                service.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                service.getUser(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id) {

        service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest request) {

        return ResponseEntity.ok(
                service.updateUser(id, request));
    }
}