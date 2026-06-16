package com.library.service;

import com.library.dto.AuthResponse;
import com.library.dto.LoginRequest;
import com.library.dto.RegisterRequest;
import com.library.entity.User;
import com.library.repository.UserRepository;
import com.library.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Override
    public void register(
            RegisterRequest request) {

        if(repository.findByEmail(
                        request.getEmail())
                .isPresent()) {

            throw new RuntimeException(
                    "Email already registered");
        }

        if(request.getRole() == null) {

            throw new RuntimeException(
                    "Role is required");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        encoder.encode(
                                request.getPassword()))
                .role(request.getRole())
                .build();

        repository.save(user);
    }

    @Override
    public AuthResponse login(
            LoginRequest request) {

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        User user =
                repository.findByEmail(
                                request.getEmail())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "User not found"));

        String token =
                jwtService.generateToken(
                        request.getEmail());

        return new AuthResponse(
                token,
                user.getRole().name(),
        user.getId());
    }
}