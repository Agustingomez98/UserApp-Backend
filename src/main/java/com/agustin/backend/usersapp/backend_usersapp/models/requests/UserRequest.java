package com.agustin.backend.usersapp.backend_usersapp.models.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRequest {
    @NotBlank
    private String username;
    
    @Email
    @NotBlank
    private String email;
}
