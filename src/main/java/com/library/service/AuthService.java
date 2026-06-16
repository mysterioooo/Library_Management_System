package com.library.service;

import com.library.dto.*;

public interface AuthService {

    void register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}