package com.library.dto;

import com.library.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private Role role;
}