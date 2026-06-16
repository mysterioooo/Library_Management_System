package com.library.dto;

import com.library.entity.Role;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private Role role;
}
