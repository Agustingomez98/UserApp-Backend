package com.agustin.backend.usersapp.backend_usersapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agustin.backend.usersapp.backend_usersapp.config.service.UserDetailsServiceImpl;
import com.agustin.backend.usersapp.backend_usersapp.controllers.dto.AuthCreateUserRequest;
import com.agustin.backend.usersapp.backend_usersapp.controllers.dto.AuthLoginRequest;
import com.agustin.backend.usersapp.backend_usersapp.controllers.dto.AuthResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @RequestMapping("/sign-up")
    public ResponseEntity<AuthResponse> register (@Valid @RequestBody AuthCreateUserRequest authCreateUser){
        return new ResponseEntity<>(this.userDetailsServiceImpl.createUser(authCreateUser), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login (@Valid @RequestBody AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailsServiceImpl. loginUser(userRequest),HttpStatus.OK);
    }

}
