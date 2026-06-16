package com.library.service;

import com.library.dto.DashboardDTO;
import com.library.dto.ReservationRequest;
import com.library.dto.UserRequest;
import com.library.dto.UserResponseDTO;
import com.library.entity.User;

import java.util.List;

public interface UserService {

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO getUser(Long id);

    void deleteUser(Long id);

    DashboardDTO dashboard();

    void reserveBook(ReservationRequest request);

    User updateUser(Long id, UserRequest request);
}