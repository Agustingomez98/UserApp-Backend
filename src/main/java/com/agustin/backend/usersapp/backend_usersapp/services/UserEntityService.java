package com.agustin.backend.usersapp.backend_usersapp.services;

import java.util.List;
import java.util.Optional;

import com.agustin.backend.usersapp.backend_usersapp.models.entities.UserEntity;
import com.agustin.backend.usersapp.backend_usersapp.models.requests.UserRequest;

public interface UserEntityService {

    List<UserEntity> findAll();

    Optional<UserEntity> findById(Long id);

    UserEntity save (UserEntity user);

    Optional<UserEntity> update(UserRequest user, Long id);

    void remove(Long id);
}
