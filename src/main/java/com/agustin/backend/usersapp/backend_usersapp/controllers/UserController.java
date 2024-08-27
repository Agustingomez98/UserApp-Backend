package com.agustin.backend.usersapp.backend_usersapp.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agustin.backend.usersapp.backend_usersapp.models.entities.UserEntity;
import com.agustin.backend.usersapp.backend_usersapp.models.requests.UserRequest;
import com.agustin.backend.usersapp.backend_usersapp.services.UserEntityService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    @Autowired
    private UserEntityService service;

    @GetMapping()
    public List<UserEntity> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<UserEntity> userOptional = service.findById(id);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<?> createUser(@Valid @RequestBody UserEntity user,BindingResult result) { 
        if (result.hasErrors()) {
            return validation(result);            
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest user,BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<UserEntity> userOptional = service.update(user, id);
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping( "/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable Long id){

        Optional<UserEntity> userOptional=  service.findById(id);
        if (userOptional.isPresent()) {
            service.remove(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String,String> errors = new HashMap<>();

        result.getFieldErrors().forEach(e -> {
            errors.put(e.getField(), "El campo "+ e.getField()+ " " + e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}
