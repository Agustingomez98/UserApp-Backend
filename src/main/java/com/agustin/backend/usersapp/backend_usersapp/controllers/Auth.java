package com.agustin.backend.usersapp.backend_usersapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.agustin.backend.usersapp.backend_usersapp.services.UserEntityService;

@RestController
@RequestMapping("/methods")
public class Auth {

    @Autowired
    private UserEntityService service;
    
    

}
