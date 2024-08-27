package com.agustin.backend.usersapp.backend_usersapp.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(@NotBlank String username,@NotBlank String password) {

}
